package com.osekiller.projet.model;


import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String name;
    private String email;
    private String password;

    protected User(){

    }
    protected User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
