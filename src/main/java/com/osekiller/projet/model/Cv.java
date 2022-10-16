package com.osekiller.projet.model;

import com.osekiller.projet.model.user.Student;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class Cv {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private byte[] pdf ;

    private String pdfName;

    @OneToOne(cascade = CascadeType.MERGE)
    @NonNull private Student owner;

    @NonNull private boolean validated;

    private String feedback;
}
