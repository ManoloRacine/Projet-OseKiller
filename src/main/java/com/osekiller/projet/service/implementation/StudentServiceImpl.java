package com.osekiller.projet.service.implementation;

import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.model.user.User;
import com.osekiller.projet.repository.user.StudentRepository;
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

    private StudentRepository studentRepository;

    private final Path cvPath = Paths.get("CV");

    @Override
    public void validateCV(Long studentId) {

    }

    @Override
    public void invalidateCV(Long studentId) {

    }

    @Override
    public void saveCV(MultipartFile cv, Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);

        if (student.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        try {
            Files.copy(cv.getInputStream(), cvPath.resolve(studentId + ".pdf"));
            CV newCV = cvRepository.save(new CV(cvPath.toString(), student.get(), false));
            student.get().setCv(newCV);
            student.get().setCvRejected(false);
            studentRepository.save(student.get());
        } catch (IOException e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public Resource getCV(Long studentId, ResourceFactory resourceFactory) {
        try {
            Path file = cvPath.resolve(studentId.toString() + ".pdf");
            Resource resource = resourceFactory.createResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
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
