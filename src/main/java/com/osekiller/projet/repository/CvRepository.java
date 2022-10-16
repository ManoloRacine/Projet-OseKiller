package com.osekiller.projet.repository;

import com.osekiller.projet.model.Cv;
import com.osekiller.projet.model.user.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CvRepository extends CrudRepository<Cv, Long> {

    @Override
    List<Cv> findAll();

    Cv findCVByOwner(Student owner);
}
