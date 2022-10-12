package com.osekiller.projet.service;

import com.osekiller.projet.model.Cv;
import com.osekiller.projet.model.Role;
import com.osekiller.projet.model.user.Manager;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.CvRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.repository.user.UserRepository;
import com.osekiller.projet.service.implementation.StudentServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @Mock
    CvRepository cvRepository;

    @InjectMocks
    private StudentServiceImpl studentService ;

    @Test
    void saveCVHappyDay() {
        // Arrange
        Student mockStudent = new Student("Joe Biden","jbiden@osk.com","password");
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "test".getBytes()
        ) ;
        Cv mockCv = new Cv(mockStudent, false);
        when(studentRepository.findById(any())).thenReturn(Optional.of(mockStudent));
        when(cvRepository.findCVByOwner(any())).thenReturn(mockCv);

        // Act
        studentService.saveCV(mockFile, 1L);

        // Assert
        verify(studentRepository).save(any(Student.class)) ;
        assertThat(cvRepository.findCVByOwner(mockStudent)).isNotNull();
    }

    @Test
    void saveCVNotStudent() {
        //Arrange
        Manager mockManager = new Manager("Joe Biden","jbiden@osk.com","password");
        mockManager.setRole(new Role("MANAGER"));
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes()) ;

        //Act & Assert
        assertThatThrownBy(() -> studentService.saveCV(mockFile, 1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.UNAUTHORIZED);


    }

    @Test
    void getCVHappyDay() throws IOException {
        //Arrange
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "test".getBytes()
        ) ;
        Cv cv = mock(Cv.class) ;
        Student student = mock(Student.class) ;
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student)) ;
        when(student.getCv()).thenReturn(cv) ;
        when(cvRepository.findById(anyLong())).thenReturn(Optional.of(cv)) ;
        when(cv.getId()).thenReturn(1L) ;
        when(cv.getPdf()).thenReturn(mockFile.getBytes()) ;

        //Act
        Resource resourceReturn = studentService.getCV(1L) ;

        Assertions.assertThat(resourceReturn.getClass()).isEqualTo(ByteArrayResource.class) ;
    }

    @Test
    void getCVDoesntExist() {
        //Act & Assert
        assertThatThrownBy(() -> studentService.getCV(1L) )
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);

    }

}
