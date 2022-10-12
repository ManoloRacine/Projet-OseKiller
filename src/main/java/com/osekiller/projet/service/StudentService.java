package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.response.StudentDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
