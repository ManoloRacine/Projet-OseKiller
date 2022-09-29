package com.osekiller.projet.service.implementation;

import com.osekiller.projet.model.user.User;
import com.osekiller.projet.repository.user.UserRepository;
import com.osekiller.projet.service.ResourceFactory;
import com.osekiller.projet.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private UserRepository userRepository;

    private final Path cvPath = Paths.get("CV") ;

    @Override
    public void validateStudentCv(Long studentId) {

    }

    @Override
    public void invalidateStudentCv(Long studentId) {

    }

    @Override
    public void saveCV(MultipartFile cv, Long studentId) {
        Optional<User> user = userRepository.findById(studentId) ;

        if (user.isEmpty() || !user.get().getRole().getName().equals("STUDENT")) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED) ;

        try {
            Files.copy(cv.getInputStream(), cvPath.resolve(studentId + ".pdf"));
        }
        catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR) ;
        }

    }

    public Resource getCV(Long studentId, ResourceFactory resourceFactory) {
        try {
            Path file = cvPath.resolve(studentId.toString() + ".pdf") ;
            Resource resource = resourceFactory.createResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource ;
            }
            else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR) ;
            }
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR) ;
        }
    }

    public void init() {
        try {
            Files.createDirectory(cvPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(cvPath.toFile());
    }

}
