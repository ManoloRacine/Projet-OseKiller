package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.controller.payload.response.StudentWithCvStateDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {

    void validateCV(Long studentId, String feedback);

    void invalidateCV(Long studentId, String feedback);

    void saveCV(MultipartFile cv, Long studentId);

    Resource getCV(Long studentId);

    List<StudentWithCvStateDto> getStudents();

    StudentWithCvStateDto getStudent(long id);

}
