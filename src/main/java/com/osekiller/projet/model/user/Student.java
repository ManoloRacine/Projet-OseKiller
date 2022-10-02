package com.osekiller.projet.model.user;

import com.osekiller.projet.model.CV;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Data
public class Student extends User {

    @OneToOne
    CV cv = new CV() ;

    private boolean cvRejected ;

    public Student(@NonNull String name, @NonNull String email, @NonNull String password) {
        super(name, email, password);
    }


}
