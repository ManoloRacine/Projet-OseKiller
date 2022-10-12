package com.osekiller.projet.model;

import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.model.user.Student;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @NonNull private Double salary;
    @NonNull private LocalDate startDate;
    @NonNull private LocalDate endDate;

    @ManyToMany
    private List<Student> applicants = new ArrayList<>();

    private boolean accepted = false;
}
