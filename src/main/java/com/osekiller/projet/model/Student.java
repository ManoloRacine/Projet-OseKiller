package com.osekiller.projet.model;

import lombok.*;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Data
public class Student extends User{
    public Student(@NonNull String name, @NonNull String email, @NonNull String password) {
        super(name, email, password);
    }
}
