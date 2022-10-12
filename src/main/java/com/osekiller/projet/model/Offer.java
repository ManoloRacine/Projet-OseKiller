package com.osekiller.projet.model;

import com.osekiller.projet.model.user.Company;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Data
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @NonNull
    private Company owner;

    private String pdfName;

    @Lob
    private byte[] pdf;

    @NonNull private String position;
    @NonNull private double salary;
    @NonNull private LocalDate startDate;
    @NonNull private LocalDate endDate;

    @NonNull private boolean accepted;
}
