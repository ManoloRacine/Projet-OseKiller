package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.response.ContractToEvaluateDto;
import com.osekiller.projet.controller.payload.response.EvaluationSimpleDto;
import com.osekiller.projet.service.implementation.ContractServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ManagerControllerTest {

    @MockBean
    private ContractServiceImpl contractService;

    @Autowired
    private MockMvc mockMvc ;

    @Test
    @WithMockUser(authorities = {"MANAGER"})
    void getEvaluationsHappyDay() throws Exception {
        //Arrange
        List<EvaluationSimpleDto> evaluationSimpleDtoList = new ArrayList<>() ;
        for (int i = 0; i < 3; i++) {
            evaluationSimpleDtoList.add(mock(EvaluationSimpleDto.class));
        }
        when(contractService.getEvaluations()).thenReturn(evaluationSimpleDtoList) ;

        //Act & Assert
        mockMvc.perform(get("/manager/evaluations")).
                andExpect(status().isOk()) ;
    }

    @Test
    @WithMockUser(authorities = {"MANAGER"})
    void getEvaluationPdfHappyDay() throws Exception {
        //Arrange
        when(contractService.getEvaluationPdf(anyLong())).thenReturn(new ByteArrayResource(new byte[16])) ;

        //Act & Assert
        mockMvc.perform(get("/manager/{contractId}/evaluation-pdf", 1)).
                andExpect(status().isOk()) ;
    }

    @Test
    @WithMockUser(authorities = {"MANAGER"})
    void getEvaluationPdfNotFound() throws Exception {
        //Arrange
        when(contractService.getEvaluationPdf(anyLong())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)) ;

        //Act & Assert
        mockMvc.perform(get("/manager/{contractId}/evaluation-pdf", 1)).
                andExpect(status().isNotFound()) ;
    }
}
