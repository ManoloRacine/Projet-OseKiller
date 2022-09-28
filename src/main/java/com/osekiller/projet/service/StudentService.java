package com.osekiller.projet.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

public interface StudentService {
    void saveCV(MultipartFile cv, Long studentId) ;
    Resource getCV() ;
}
