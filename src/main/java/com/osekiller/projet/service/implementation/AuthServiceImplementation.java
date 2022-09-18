package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.request.CompanySignUpRequest;
import com.osekiller.projet.controller.request.ManagerSignUpRequest;
import com.osekiller.projet.controller.request.StudentSignUpRequest;
import com.osekiller.projet.model.Company;
import com.osekiller.projet.model.Manager;
import com.osekiller.projet.model.Student;
import com.osekiller.projet.repository.user.CompanyRepository;
import com.osekiller.projet.repository.user.ManagerRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AuthServiceImplementation {
    private CompanyRepository companyRepository;
    private StudentRepository studentRepository;
    private ManagerRepository managerRepository;

    //TODO Refaire l'inscription pour inclure une conformation d'un gestionnaire
    public void signUpStudent(StudentSignUpRequest request){
        if(studentRepository.findByEmail(request.email()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT,"email-taken");
        studentRepository.save(Student.from(request));
    }

    public void signUpManager(ManagerSignUpRequest request){
        if(managerRepository.findByEmail(request.email()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT,"email-taken");
        managerRepository.save(Manager.from(request));
    }

    public void signUpCompany(CompanySignUpRequest request){
        if(companyRepository.findByEmail(request.email()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT,"email-taken");
        companyRepository.save(Company.from(request));
    }
}
