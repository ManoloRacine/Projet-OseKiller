package com.osekiller.projet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.model.user.Student;
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
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
    StudentController controller = new StudentController(studentService);

    @Test
    @WithMockUser(authorities = {"MANAGER"})
    void getCVHappyDay() throws Exception {
        //Arrange
        ClassLoader classLoader = controller.getClass().getClassLoader();
        boolean file = new File(classLoader.getResource(".").getFile() + "/" + FILE_NAME).createNewFile();
        inputStream = classLoader.getResourceAsStream(FILE_NAME);
        Resource resource = new InputStreamResource(inputStream);
        when(studentService.getCV(anyLong())).thenReturn(resource);

        //Act & Assert
        mockMvc.perform(get("/students/{id}/cv", 1L))
                .andExpect(status().isOk()) ;

    }
    @Test
    @WithMockUser(authorities = {"STUDENT"})
    void saveCVHappyDay() throws Exception {
        //Arrange
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes()) ;

        //Act & Assert
        mockMvc.perform(put("/students/{id}/cv", 1L)
                        .content(mockMultipartFile.getBytes()))
                .andExpect(status().isOk()) ;
    }
    @Test
    @WithMockUser(authorities = {"STUDENT"})
    void getApplicationsHappyDay() throws Exception {
        //Arrange

        Student student = new Student("Joe", "jbiden@osk.com", "password");

        Company company = mock(Company.class);
        Offer offer1 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16));
        Offer offer2 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16));
        Offer offer3 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16));
        student.setApplications(List.of(offer1, offer2, offer3));

        List<GeneralOfferDto> applications = student.getApplications().stream().map(GeneralOfferDto::from).toList();

        String expected = asJsonString(applications);

        when(studentService.getApplications(anyLong())).thenReturn(applications);

        //Act & Assert

        MvcResult result = mockMvc.perform(get("/students/1/applications"))
                .andExpect(status().isOk())
                .andReturn();

        String actual = result.getResponse().getContentAsString();

        assertThat(actual)
                .isNotBlank()
                .isEqualTo(expected);
    }
    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
