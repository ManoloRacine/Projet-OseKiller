package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.request.*;
import com.osekiller.projet.model.*;
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
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;
    private CompanyRepository companyRepository;
    private StudentRepository studentRepository;
    private ManagerRepository managerRepository;
    private UserRepository userRepository;

    @Override
    public String signIn(SignInDto dto) {
        UserDetails user = loadUserByUsername(dto.email());
        return jwtUtils.generateToken(user);
    }

    @Override
    public User signUp(SignUpDto dto) {
        //Valider que le email est disponble
        if(userRepository.findByEmail(dto.email()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"email-taken");
        }

        //Sauvegarder l'utilisateur en fonction de son role
        if(dto.role().equals(ERole.STUDENT.name())){
            return studentRepository.save(new Student(dto.name(), dto.email(), passwordEncoder.encode(dto.password())));
        }
        if (dto.role().equals(ERole.MANAGER.name())){
            return managerRepository.save(new Manager(dto.name(), dto.email(), passwordEncoder.encode(dto.password())));
        }
        if(dto.role().equals(ERole.COMPANY.name())){
            return companyRepository.save(new Company(dto.name(), dto.email(), passwordEncoder.encode(dto.password())));
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User " + username + " not found.")
                );
    }
}
