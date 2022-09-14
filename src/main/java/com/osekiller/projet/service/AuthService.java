package com.osekiller.projet.service;

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
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private CompanyRepository companyRepository;
    private StudentRepository studentRepository;
    private ManagerRepository managerRepository;

    //TODO Refaire l'inscription pour inclure une conformation d'un gestionnaire
    public Student signUpStudent(StudentSignUpRequest request){
        return studentRepository.save(Student.from(request));
    }

    public Manager signUpManager(ManagerSignUpRequest request){
        return managerRepository.save(Manager.from(request));
    }

    public Company signUpCompany(CompanySignUpRequest request){
        return companyRepository.save(Company.from(request));
    }
}
