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

    private Long id;

    public Student() {
    }

    public Student(String name, String email, String password) {
        super(name, email, password);
    }

    public static Student from(StudentSignUpRequest request) {
        return new Student(request.firstName(), request.lastname(), request.email(), request.password());
    }
}
