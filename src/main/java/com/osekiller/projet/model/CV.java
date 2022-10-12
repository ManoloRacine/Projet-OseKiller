package com.osekiller.projet.model;

import com.osekiller.projet.model.user.Student;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class CV {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private byte[] pdf ;

    private String pdfName;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull private Student owner;

    @NonNull private boolean isValidated;

    private String feedback;
}
