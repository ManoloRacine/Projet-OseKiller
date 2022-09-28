package com.osekiller.projet.service.implementation;

import com.osekiller.projet.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final Path cvPath = Paths.get("CV") ;
    @Override
    public void saveCV(MultipartFile cv, Long studentId) {
        try {
            Files.copy(cv.getInputStream(), cvPath.resolve("1_" + cv.getOriginalFilename()));
        }
        catch (IOException e) {
            //todo what exception ?
        }

    }

    @Override
    public Resource getCV() {
        return null ;
    }
}
