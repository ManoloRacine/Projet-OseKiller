package com.osekiller.projet.controller;

import com.osekiller.projet.repository.user.UserRepository;
import com.osekiller.projet.service.implementation.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentControllerTest {

    @MockBean
    private StudentServiceImpl studentService ;

    @Autowired
    MockMvc mockMvc ;

    @Test
    @WithMockUser
    void getCVHappyDay() throws Exception {
        Resource resource = mock(Resource.class) ;
        doReturn(resource).when(studentService).getCV(any(), any()) ;
        doReturn("test").when(resource).getFilename() ;

        mockMvc.perform(get("/students/{id}/cv", 1L))
                .andExpect(status().isOk()) ;

    }

    @Test
    @WithMockUser
    void saveCVHappyDay() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes()) ;
        mockMvc.perform(put("/students/{id}/cv", 1L)
                        .content(mockMultipartFile.getBytes()))
                .andExpect(status().isOk()) ;
    }
}
