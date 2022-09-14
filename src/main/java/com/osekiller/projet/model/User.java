package com.osekiller.projet.model;


import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class User {
    private String email;
    private String password;

    protected User(){

    }
    protected User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
