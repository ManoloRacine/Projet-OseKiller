package com.osekiller.projet.model;


import lombok.Data;

@Data
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
