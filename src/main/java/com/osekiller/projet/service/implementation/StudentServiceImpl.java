package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.payload.response.StudentDto;
import com.osekiller.projet.model.CV;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.CVRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.service.ResourceFactory;
import com.osekiller.projet.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
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
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    private CVRepository cvRepository;

    private final Path cvPath = Paths.get("CV");

    @Override
    public void validateCV(Long studentId, String feedback) {
        Optional<Student> student = studentRepository.findById(studentId);

        if (student.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        student.get().setCvRejected(false);
        student.get().getCv().setValidated(true);
        student.get().getCv().setFeedback(feedback);
        studentRepository.save(student.get());
    }

    @Override
    public void invalidateCV(Long studentId, String feedback) {
        Optional<Student> student = studentRepository.findById(studentId);

        if (student.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        student.get().setCvRejected(true);
        student.get().getCv().setValidated(false);
        student.get().getCv().setFeedback(feedback);
        studentRepository.save(student.get());
    }

    @Override
    public void saveCV(MultipartFile cv, Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);

        if (student.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        try {
            Path path = cvPath.resolve(studentId + ".pdf");
            if (path.toFile().exists()) {
                Files.delete(path);
            }
            Files.copy(cv.getInputStream(), path);
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
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            }
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public List<StudentDto> getStudents() {
        return studentRepository.findAll().stream().map(
                StudentDto::from
        ).toList() ;
    }
    public StudentDto getStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return StudentDto.from(student);
    }

    public void init() {
        try {
            Files.createDirectory(cvPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public void deleteAll() {
        cvRepository.deleteAll();
        FileSystemUtils.deleteRecursively(cvPath.toFile());
    }

}
