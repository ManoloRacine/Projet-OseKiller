package com.osekiller.projet.repository;

import com.osekiller.projet.model.Contract;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends CrudRepository<Contract, Long> {
    Optional<Contract> findByStudent_IdAndOffer_Id(Long student_id, Long offer_id);

    List<Contract> findAllByEvaluationPdfIsNull() ;
}
