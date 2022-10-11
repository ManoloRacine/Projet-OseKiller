package com.osekiller.projet.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StudentService {

    void validateCV(Long studentId, String feedback);

    void invalidateCV(Long studentId, String feedback);

    void saveCV(MultipartFile cv, Long studentId);

    Resource getCV(Long studentId);

    void init();

    void deleteAll();

}
