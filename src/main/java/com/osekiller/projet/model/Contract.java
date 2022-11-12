package com.osekiller.projet.model;

import com.osekiller.projet.model.user.Manager;
import com.osekiller.projet.model.user.Student;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @NonNull
    private Student student;

    @ManyToOne(cascade = CascadeType.MERGE)
    @NonNull
    private Offer offer;

    @ManyToOne(cascade = CascadeType.MERGE)
    @NonNull
    private Manager manager;

    private boolean signedByStudent;
    private boolean signedByCompany;
    private boolean signedByManager;

    @Lob
    private byte[] pdf;
}
