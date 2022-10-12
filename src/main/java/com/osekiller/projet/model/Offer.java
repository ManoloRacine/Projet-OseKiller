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

    @ManyToOne(cascade = CascadeType.MERGE)
    @NonNull
    private Company owner;

    private String path;

    @NonNull private String position;
    private String feedback;
    @NonNull private Double salary;
    @NonNull private LocalDate startDate;
    @NonNull private LocalDate endDate;

    private Boolean accepted = false;
}
