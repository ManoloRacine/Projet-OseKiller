package com.osekiller.projet.repository.user;

import com.osekiller.projet.model.user.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>{
}
