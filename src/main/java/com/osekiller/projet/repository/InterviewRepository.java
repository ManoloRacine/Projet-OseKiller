package com.osekiller.projet.repository;

import com.osekiller.projet.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findAllByInterviewee_Id(long id);
}
