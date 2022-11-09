package com.osekiller.projet.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Data
public abstract class Signatory extends User{
    private byte[] signature;
    public Signatory(@NonNull String name, @NonNull String email, @NonNull String password) {
        super(name, email, password);
    }
}
