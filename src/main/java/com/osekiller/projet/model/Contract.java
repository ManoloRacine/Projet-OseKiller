package com.osekiller.projet.model;

import com.osekiller.projet.model.user.Manager;
import com.osekiller.projet.model.user.Student;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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

    private LocalDate studentSigningDate;
    private LocalDate managerSigningDate;
    private LocalDate companySigningDate;

    @Lob
    private byte[] pdf;

    @Lob
    private byte[] evaluationPdf;
}
