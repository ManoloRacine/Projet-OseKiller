package com.osekiller.projet.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Manager extends User{
    @Id
    private Long id;
}
