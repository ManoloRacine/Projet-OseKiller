package com.osekiller.projet.model;

import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.model.user.Student;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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

    private String path;

    @NonNull private String position;
    @NonNull private double salary;
    @NonNull private LocalDate startDate;
    @NonNull private LocalDate endDate;

    @NonNull private boolean accepted;
}
