package com.osekiller.projet.repository;

import com.osekiller.projet.model.CV;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CVRepository extends CrudRepository<CV, Long> {

    @Override
    List<CV> findAll();
}
