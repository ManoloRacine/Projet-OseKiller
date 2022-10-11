package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.response.StudentDto;
import com.osekiller.projet.model.user.Student;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    void validateCV(Long studentId, String feedback);

    void invalidateCV(Long studentId, String feedback);

    void saveCV(MultipartFile cv, Long studentId);

    Resource getCV(Long studentId);

    List<StudentDto> getStudents();

    StudentDto getStudent(Long id);
    void init(); //TODO this should be in another service like "FileStorageService"

    void deleteAll(); //TODO this should be in another service like "FileStorageService"

}
