package com.osekiller.projet.service;

import com.osekiller.projet.repository.CompanyRepository;
import com.osekiller.projet.repository.ManagerRepository;
import com.osekiller.projet.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private CompanyRepository companyRepository;
    private StudentRepository studentRepository;
    private ManagerRepository managerRepository;

    public void login(String email, String password){

    }
}
