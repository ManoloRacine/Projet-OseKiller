package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.response.ApplicationDto;
import com.osekiller.projet.service.ContractService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ApplicationControllerTest {

    @MockBean
    ContractService contractService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = {"MANAGER"})
    void getApplicationsHappyDay () throws Exception {

        //Arrange

        List<ApplicationDto> dtos = List.of(
                mock(ApplicationDto.class),
                mock(ApplicationDto.class),
                mock(ApplicationDto.class)
        );

        when(contractService.getAcceptedApplications()).thenReturn(dtos);
        //Act & Assert

        mockMvc.perform(get("/applications?accepted=true"))
                .andExpect(status().isOk());
    }
}
