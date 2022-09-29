package com.osekiller.projet.service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

public interface StudentService {

    void validateCV(Long studentId);

    void invalidateCV(Long studentId);

    void saveCV(MultipartFile cv, Long studentId);

    Resource getCV(Long studentId, ResourceFactory resourceFactory);

    void init();

    void deleteAll();

}
