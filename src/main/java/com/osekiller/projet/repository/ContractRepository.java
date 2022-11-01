package com.osekiller.projet.repository;

import com.osekiller.projet.model.Contract;
import com.osekiller.projet.model.Cv;
import org.springframework.data.repository.CrudRepository;

public interface ContractRepository extends CrudRepository<Contract, Long> {
}
