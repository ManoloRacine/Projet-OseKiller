package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.request.*;
import com.osekiller.projet.model.*;
import com.osekiller.projet.repository.user.CompanyRepository;
import com.osekiller.projet.repository.user.ManagerRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.repository.user.UserRepository;
import com.osekiller.projet.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private CompanyRepository companyRepository;
    private StudentRepository studentRepository;
    private ManagerRepository managerRepository;
    private UserRepository userRepository;

    @Override
    public String signIn(SignInDto dto) {
        return null;
    }

    @Override
    public User signUp(SignUpDto dto) {
        //Valider que le email est disponble
        if(userRepository.findByEmail(dto.email()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"email-taken");
        }

        //Sauvegarder l'utilisateur en fonction de son role
        if(dto.role().equals(Roles.STUDENT.name())){
            return studentRepository.save(new Student(dto.name(), dto.email(), dto.password()));
        }
        if (dto.role().equals(Roles.MANAGER.name())){
            return managerRepository.save(new Manager(dto.name(), dto.email(), dto.password()));
        }
        if(dto.role().equals(Roles.COMPANY.name())){
            return companyRepository.save(new Company(dto.name(), dto.email(), dto.password()));
        }

        //Lancer un 400 si le role n'existe pas
        StringBuilder message = new StringBuilder("role not in: [ ");

        for (Roles role: Roles.values()) {
            message.append("\"").append(role.name()).append("\", ");
        }

        message = new StringBuilder(message.substring(message.length() - 2, message.length() - 1));

        message.append("]");

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message.toString());
    }
}
