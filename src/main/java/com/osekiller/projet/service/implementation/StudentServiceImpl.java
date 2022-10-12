package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.payload.response.StudentDto;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.CvRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    private CvRepository cvRepository;

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

        String fileName = StringUtils.cleanPath(cv.getOriginalFilename());
        student.get().getCv().setPdfName(fileName);

        try {
            student.get().getCv().setPdf(cv.getBytes());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        studentRepository.save(student.get()) ;

    }

    public Resource getCV(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId) ;
        if (student.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;
        if (cvRepository.findById(student.get().getCv().getId()).isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;

        Resource resource = new ByteArrayResource(student.get().getCv().getPdf());

        return resource;
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

}
