package com.osekiller.projet.repository;

import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByOwner(Company company) ;

    List<Offer> findAllByAcceptedIsTrue() ;
    List<Offer> findAllByAcceptedIsFalse() ;

    List<Offer> findAllByAcceptedIsFalseAndFeedbackIsNull();
}
