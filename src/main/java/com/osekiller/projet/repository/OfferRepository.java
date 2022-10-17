package com.osekiller.projet.repository;

import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByOwner(Company company) ;
    @Query("SELECT o FROM Offer o JOIN FETCH o.applicants WHERE o.id = (:id)")
    Optional<Offer> findByIdAndFetchApplicants(Long id);
    List<Offer> findAllByAcceptedIsTrue() ;
    List<Offer> findAllByAcceptedIsFalse() ;
    List<Offer> findAllByAcceptedIsFalseAndFeedbackIsNull();
}
