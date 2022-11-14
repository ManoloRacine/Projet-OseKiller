package com.osekiller.projet.repository.user;

import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.model.user.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>{
    Optional<Teacher> findByEmail(String email);
}