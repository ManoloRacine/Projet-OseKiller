package com.osekiller.projet.controller;

import com.osekiller.projet.service.implementation.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentControllerTest {

    private final String FILE_NAME = "file.txt";
    private InputStream inputStream;
    @MockBean
    private StudentServiceImpl studentService ;

    @Autowired
    MockMvc mockMvc ;

    @InjectMocks
    CVController controller = new CVController(studentService);

    @Test
    @WithMockUser(authorities = {"MANAGER"})
    void getCVHappyDay() throws Exception {
        ClassLoader classLoader = controller.getClass().getClassLoader();
        boolean file = new File(classLoader.getResource(".").getFile() + "/" + FILE_NAME).createNewFile();
        inputStream = classLoader.getResourceAsStream(FILE_NAME);
        Resource resource = new InputStreamResource(inputStream);
        when(studentService.getCV(any())).thenReturn(resource);

        mockMvc.perform(get("/student/{id}/cv", 1L))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(authorities = {"STUDENT"})
    void saveCVHappyDay() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes()) ;
        mockMvc.perform(put("/student/{id}/cv", 1L)
                        .content(mockMultipartFile.getBytes()))
                .andExpect(status().isOk()) ;
    }
}
