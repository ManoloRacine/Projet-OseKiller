package com.osekiller.projet.model;

import com.osekiller.projet.controller.request.CompanySignUpRequest;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Company extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String companyName;

    public Company(){
    }

    public Company (String companyName, String email, String password) {
        super(email, password);
        this.companyName = companyName;
    }

    public static Company from(CompanySignUpRequest request){
        return new Company(request.companyName(),request.email(), request.password());
    }
}
