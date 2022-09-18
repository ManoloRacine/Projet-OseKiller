package com.osekiller.projet.model;
import lombok.*;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull private String name;
    @NonNull private String email;
    @NonNull private String password;
    private boolean isActive = false;
}
