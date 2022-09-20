package com.osekiller.projet.model;

import com.osekiller.projet.controller.request.StudentSignUpRequest;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Student extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    public Student() {
    }

    public Student(String firstName, String lastName,String email, String password) {
        super(email, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static Student from(StudentSignUpRequest request) {
        return new Student(request.firstName(), request.lastname(), request.email(), request.password());
    }
}
