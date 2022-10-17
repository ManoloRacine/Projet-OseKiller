package com.osekiller.projet.repository.user;

import com.osekiller.projet.model.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
    @Query("SELECT s FROM Student s JOIN FETCH s.applications WHERE s.id = (:id)")
    Optional<Student> findByIdAndFetchApplications(long id);
}
