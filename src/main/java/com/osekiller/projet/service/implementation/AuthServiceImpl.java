package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.payload.request.SignInDto;
import com.osekiller.projet.controller.payload.request.SignUpDto;
import com.osekiller.projet.controller.payload.response.AuthPingDto;
import com.osekiller.projet.controller.payload.response.JwtResponseDto;
import com.osekiller.projet.controller.payload.response.UsersDto;
import com.osekiller.projet.model.*;
import com.osekiller.projet.repository.RefreshTokenRepository;
import com.osekiller.projet.repository.user.CompanyRepository;
import com.osekiller.projet.repository.user.ManagerRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.repository.user.UserRepository;
import com.osekiller.projet.security.JwtUtils;
import com.osekiller.projet.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {
    private PasswordEncoder passwordEncoder;
    private RefreshTokenRepository refreshTokenRepository;
    private JwtUtils jwtUtils;
    private CompanyRepository companyRepository;
    private StudentRepository studentRepository;
    private ManagerRepository managerRepository;
    private UserRepository userRepository;

    @Override
    public JwtResponseDto signIn(SignInDto dto) {
        User user = userRepository.findByEmail(dto.email()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        RefreshToken refreshToken = new RefreshToken(
                UUID.randomUUID().toString(),
                user,
                Instant.now().plusMillis(RefreshToken.TOKEN_EXPIRATION)
        );

        refreshToken = refreshTokenRepository.save(refreshToken);
        String accessToken = jwtUtils.generateToken(user);

        JwtResponseDto response = new JwtResponseDto(
                accessToken,
                refreshToken.getToken(),
                "Bearer"
        );

        return response;
    }

    @Override
    public void signUp(SignUpDto dto) {
        //Valider que le email est disponble
        if(userRepository.findByEmail(dto.email()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"email-taken");
        }

        //Sauvegarder l'utilisateur en fonction de son role
        if(dto.role().equals(ERole.STUDENT.name())){
            studentRepository.save(new Student(dto.name(), dto.email(), passwordEncoder.encode(dto.password())));
            return;
        }
        if (dto.role().equals(ERole.MANAGER.name())){
            managerRepository.save(new Manager(dto.name(), dto.email(), passwordEncoder.encode(dto.password())));
            return;
        }
        if(dto.role().equals(ERole.COMPANY.name())){
            companyRepository.save(new Company(dto.name(), dto.email(), passwordEncoder.encode(dto.password())));
            return;
        }

        //Lancer un 400 si le role n'existe pas
        StringBuilder message = new StringBuilder("role not in: [ ");

        for (ERole role: ERole.values()) {
            message.append("\"").append(role.name()).append("\", ");
        }

        message = new StringBuilder(message.substring(0, message.length() - 2));

        message.append(" ]");

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message.toString());
    }

    @Override
    public void signOut(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        refreshTokenRepository.delete(refreshToken);
    }
    @Override
    public JwtResponseDto refresh(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        refreshToken = verifyExpiration(refreshToken);
        JwtResponseDto response = new JwtResponseDto(
                jwtUtils.generateToken(refreshToken.getUser()),
                refreshToken.getToken(),
                "Bearer"
        );
        return response;
    }

    @Override
    public AuthPingDto authPing(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        refreshToken = verifyExpiration(refreshToken);
        return new AuthPingDto(
                refreshToken.getUser().getEmail(),
                refreshToken.getUser().getRole().getName()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User " + username + " not found.")
                );
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "token-expired");
        }

        return token;
    }

    @Override
    public List<UsersDto> getUsers() {
        return userRepository.findAll().stream().map(
                user -> new UsersDto(user.getEmail(), user.getName(), user.isEnabled())
        ).toList() ;
    }

    public void validateUser(String emailValidated) {
        User user = userRepository.findByEmail(emailValidated).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setEnabled(true);
        userRepository.save(user) ;
    }
}
