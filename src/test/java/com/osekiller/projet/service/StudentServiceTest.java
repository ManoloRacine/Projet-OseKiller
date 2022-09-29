package com.osekiller.projet.service;

import com.osekiller.projet.model.Role;
import com.osekiller.projet.model.user.Manager;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.user.UserRepository;
import com.osekiller.projet.service.implementation.StudentServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class StudentServiceTest {
    @InjectMocks
    private StudentServiceImpl studentService ;

    @Mock
    UserRepository userRepository ;

    @Test
    void saveCVHappyDay() {
        Student mockStudent = new Student("Joe Biden","jbiden@osk.com","password");
        mockStudent.setRole(new Role("STUDENT"));
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes()) ;
        when(userRepository.findById(any())).thenReturn(Optional.of(mockStudent));


        try (MockedStatic mockedStatic = mockStatic(Files.class)) {
            studentService.saveCV(mockFile, 1L);
            mockedStatic.verify(() -> Files.copy(any(ByteArrayInputStream.class), any(Path.class))) ;
        }


    }

    @Test
    void saveCVNotStudent() {
        Manager mockManager = new Manager("Joe Biden","jbiden@osk.com","password");
        mockManager.setRole(new Role("MANAGER"));
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes()) ;
        when(userRepository.findById(any())).thenReturn(Optional.of(mockManager));


        assertThatThrownBy(() -> studentService.saveCV(mockFile, 1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.UNAUTHORIZED);


    }

    @Test
    void getCVHappyDay() {
        Resource resource = mock(Resource.class) ;
        ResourceFactory factory = uri -> resource;
        when(resource.exists()).thenReturn(true) ;
        when(resource.isReadable()).thenReturn(true) ;

        Resource resourceReturn = studentService.getCV(1L, factory) ;

        Assertions.assertThat(resourceReturn).isEqualTo(resource) ;
    }

    @Test
    void getCVDoesntExist() {
        Resource resource = mock(Resource.class) ;
        ResourceFactory factory = uri -> resource;
        when(resource.exists()).thenReturn(false) ;

        assertThatThrownBy(() -> studentService.getCV(1L, factory) )
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Test
    void getCVUnreadable() {
        Resource resource = mock(Resource.class) ;
        ResourceFactory factory = uri -> resource;
        when(resource.exists()).thenReturn(true) ;
        when(resource.isReadable()).thenReturn(false) ;

        assertThatThrownBy(() -> studentService.getCV(1L, factory) )
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
