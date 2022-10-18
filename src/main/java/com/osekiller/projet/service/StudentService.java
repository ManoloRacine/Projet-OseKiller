package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.controller.payload.response.StudentDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {

    void validateCV(long studentId, String feedback);

    void invalidateCV(long studentId, String feedback);

    List<GeneralOfferDto> getApplications(long studentId);

    void saveCV(MultipartFile cv, long studentId);

    Resource getCV(long studentId);

    List<StudentDto> getStudents();

    StudentDto getStudent(long id);

}
