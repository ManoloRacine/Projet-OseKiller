package com.osekiller.projet.model;

import com.osekiller.projet.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@NoArgsConstructor
@Data
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class RefreshToken {
    public static final long TOKEN_EXPIRATION = 1000 * 60 * 60 * 24; //24 heures

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull private String token;

    @OneToOne
    @NonNull private User user;

    @NonNull private Instant expiryDate;

}
