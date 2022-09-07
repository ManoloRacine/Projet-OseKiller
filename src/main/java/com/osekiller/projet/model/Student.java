package com.osekiller.projet.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Student extends User{
    @Id
    private Long id;


}
