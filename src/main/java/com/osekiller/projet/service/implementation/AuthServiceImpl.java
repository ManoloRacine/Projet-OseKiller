package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.payload.request.SignInDto;
import com.osekiller.projet.controller.payload.request.SignUpDto;
import com.osekiller.projet.controller.payload.response.JwtResponseDto;
import com.osekiller.projet.controller.payload.response.UserDto;
import com.osekiller.projet.model.ERole;
import com.osekiller.projet.model.RefreshToken;
import com.osekiller.projet.model.Role;
import com.osekiller.projet.model.user.*;
import com.osekiller.projet.repository.RefreshTokenRepository;
import com.osekiller.projet.repository.user.*;
import com.osekiller.projet.security.JwtUtils;
import com.osekiller.projet.service.AuthService;
import com.osekiller.projet.service.NotificationsService;
import com.osekiller.projet.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
        private PasswordEncoder passwordEncoder;
        private RefreshTokenRepository refreshTokenRepository;
        private JwtUtils jwtUtils;
        private CompanyRepository companyRepository;
        private StudentRepository studentRepository;
        private ManagerRepository managerRepository;

        private TeacherRepository teacherRepository;
        private UserRepository userRepository;

        private StudentService studentService;

        private RoleRepository roleRepository;

        private NotificationsService notificationsService ;

        private final int LAST_MONTH = 5 ;
        private final int LAST_DAY = 31 ;

        @Override
        public JwtResponseDto signIn(SignInDto dto) {
                User user = userRepository.findByEmail(dto.email())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

                RefreshToken refreshToken = new RefreshToken(
                                UUID.randomUUID().toString(),
                                user,
                                Instant.now().plusMillis(RefreshToken.TOKEN_EXPIRATION));

                refreshToken = refreshTokenRepository.save(refreshToken);
                String accessToken = jwtUtils.generateToken(user);

                return new JwtResponseDto(
                                accessToken,
                                refreshToken.getToken(),
                                "Bearer");
        }

        @Override
        public void signUp(SignUpDto dto) {
                // Valider que le email est disponble
                if (userRepository.findByEmail(dto.email()).isPresent()) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT);
                }

                // Sauvegarder l'utilisateur en fonction de son role
                if (dto.role().equals(ERole.STUDENT.name())) {
                        Student student = new Student(dto.name(), dto.email(), passwordEncoder.encode(dto.password()));
                        Role studentRole = roleRepository.findByName(ERole.STUDENT.name())
                                        .orElseThrow(EntityNotFoundException::new);
                        student.setRole(studentRole);
                        student.setSessionYear(studentService.getCurrentSession());
                        studentRepository.save(student);
                        notificationsService.addNotificationForRole(ERole.MANAGER.name(),
                                "L'utilisateur : " + dto.name() + ", est en attente d'approbation");
                        return;
                }
                if (dto.role().equals(ERole.MANAGER.name())) {
                        Manager manager = new Manager(dto.name(), dto.email(), passwordEncoder.encode(dto.password()));
                        Role managerRole = roleRepository.findByName(ERole.MANAGER.name())
                                        .orElseThrow(EntityNotFoundException::new);
                        manager.setRole(managerRole);
                        managerRepository.save(manager);
                        notificationsService.addNotificationForRole(ERole.MANAGER.name(),
                                "L'utilisateur : " + dto.name() + ", est en attente d'approbation");
                        return;
                }
                if (dto.role().equals(ERole.COMPANY.name())) {
                        Company company = new Company(dto.name(), dto.email(), passwordEncoder.encode(dto.password()));
                        Role companyRole = roleRepository.findByName(ERole.COMPANY.name())
                                        .orElseThrow(EntityNotFoundException::new);
                        company.setRole(companyRole);
                        companyRepository.save(company);
                        notificationsService.addNotificationForRole(ERole.MANAGER.name(),
                                "L'utilisateur : " + dto.name() + ", est en attente d'approbation");
                        return;
                }
                if (dto.role().equals(ERole.TEACHER.name())) {
                        Teacher teacher = new Teacher(dto.name(), dto.email(), passwordEncoder.encode(dto.password())) ;
                        Role teacherRole = roleRepository.findByName(ERole.TEACHER.name())
                                        .orElseThrow(EntityNotFoundException::new) ;
                        teacher.setRole(teacherRole);
                        teacherRepository.save(teacher);
                        notificationsService.addNotificationForRole(ERole.MANAGER.name(),
                                "L'utilisateur : " + dto.name() + ", est en attente d'approbation");
                        return;
                }

                // Lancer un 400 si le role n'existe pas
                StringBuilder message = new StringBuilder("role not in: [ ");

                for (ERole role : ERole.values()) {
                        message.append("\"").append(role.name()).append("\", ");
                }

                message = new StringBuilder(message.substring(0, message.length() - 2));

                message.append(" ]");

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message.toString());
        }

        @Override
        public void signOut(String token) {
                RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
                refreshTokenRepository.delete(refreshToken);
        }

        @Override
        public JwtResponseDto refresh(String token) {
                RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
                verifyExpiration(refreshToken);
                return new JwtResponseDto(
                                jwtUtils.generateToken(refreshToken.getUser()),
                                refreshToken.getToken(),
                                "Bearer");
        }

        @Override
        public UserDto getUserFromToken(String accessToken) {
                String email = jwtUtils.getUsernameFromToken(accessToken);
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
                return new UserDto(
                                user.getEmail(),
                                user.getName(),
                                user.isEnabled(),
                                user.getId(),
                                user.getRole().getName());
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username)
                                .orElseThrow(
                                                () -> new UsernameNotFoundException(
                                                                "User " + username + " not found."));
        }

        private void verifyExpiration(RefreshToken token) {
                if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
                        refreshTokenRepository.delete(token);
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "token-expired");
                }
        }
}
