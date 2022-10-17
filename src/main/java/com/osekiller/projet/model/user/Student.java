package com.osekiller.projet.model.user;

import com.osekiller.projet.model.Cv;
import com.osekiller.projet.model.Offer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Data
public class Student extends User {

    @OneToOne(cascade = CascadeType.ALL)
    private Cv cv = new Cv();

    private boolean cvRejected ;

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Offer> applications = new ArrayList<>();

    public Student(@NonNull String name, @NonNull String email, @NonNull String password) {
        super(name, email, password);
    }


}
