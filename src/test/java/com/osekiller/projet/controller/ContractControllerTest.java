package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.response.ContractToEvaluateDto;
import com.osekiller.projet.service.AuthService;
import com.osekiller.projet.service.implementation.ContractServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ContractControllerTest {

    @MockBean
    private ContractServiceImpl contractService;

    @MockBean
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc ;

    @Test
    @WithMockUser(authorities = {"TEACHER"})
    void getUnevaluatedContractHappyDay() throws Exception {
        //Arrange
        List<ContractToEvaluateDto> contractToEvaluateDtoList = new ArrayList<>() ;
        for (int i = 0; i < 3; i++) {
            contractToEvaluateDtoList.add(mock(ContractToEvaluateDto.class));
        }

        when(contractService.getUnevaluatedContracts()).thenReturn(contractToEvaluateDtoList);

        //Act & Assert
        mockMvc.perform(get("/contracts?toEvaluate=true")
                        .header(HttpHeaders.AUTHORIZATION,"token"))
                .andExpect(status().isOk()) ;
    }

    @Test
    @WithMockUser()
    void getReportHappyDay() throws Exception {
        //Arrange
        when(contractService.getReport(1L)).thenReturn(new ByteArrayResource(new byte[16])) ;

        //Act & Assert
        mockMvc.perform(get("/contracts/{contractId}/report", 1)).
                andExpect(status().isOk()) ;
    }

    @Test
    @WithMockUser()
    void getReportNotFound() throws Exception {
        //Arrange
        when(contractService.getReport(1L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)) ;

        //Act & Assert
        mockMvc.perform(get("/contracts/{contractId}/report", 1)).
                andExpect(status().isNotFound()) ;
    }
}
