package com.osekiller.projet.model;

import com.osekiller.projet.controller.request.ManagerSignUpRequest;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Manager extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    public Manager() {
    }

    public Manager(String firstName, String lastName, String email, String password) {
        super(email, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static Manager from(ManagerSignUpRequest request){
        return new Manager(request.firstName(),request.lastname(), request.email(), request.password());
    }
}
