package com.osekiller.projet.repository.user;

import com.osekiller.projet.model.user.Signatory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SignatoryRepository extends JpaRepository<Signatory, Long> {
    Optional<Signatory> findByIdAndSignatureIsNotNull(Long id);
}
