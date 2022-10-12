package com.osekiller.projet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.controller.payload.response.OfferDtoResponseNoPdf;
import com.osekiller.projet.service.OfferService;
import com.osekiller.projet.service.implementation.CompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @MockBean
    private CompanyServiceImpl companyService ;

    @MockBean
    private OfferService offerService;

    @Autowired
    private MockMvc mockMvc ;

    @Test
    @WithMockUser
    void getOfferHappyDay() throws Exception {
        //Arrange
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        OfferDtoResponse offerDtoResponse = new OfferDtoResponse(1L, "test", 1, "2002-12-12", "2002-12-14", new InputStreamResource(mockMultipartFile.getInputStream())) ;
        doReturn(offerDtoResponse).when(offerService).getOffer(1L) ;

        //Act & Assert
        mockMvc.perform(get("/companies/{companyId}/offers/{offerId}", 1, 1)).
                andExpect(status().isOk()) ;
    }

    @Test
    @WithMockUser
    void getOfferNoCompany() throws Exception {
        //Arrange
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        OfferDtoResponse offerDtoResponse = new OfferDtoResponse(1L, "test", 1, "2002-12-12", "2002-12-14", new InputStreamResource(mockMultipartFile.getInputStream())) ;
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(offerService).getOffer(1L);

        //Act & Assert
        mockMvc.perform(get("/companies/{companyId}/offers/{offerId}", 1, 1)).
                andExpect(status().isNotFound()) ;
    }

    @Test
    @WithMockUser
    void getOffersHappyDay() throws Exception {
        //Arrange
        List<OfferDtoResponseNoPdf> offerDtoResponseList = new ArrayList<>() ;
        offerDtoResponseList.add(mock(OfferDtoResponseNoPdf.class)) ;
        offerDtoResponseList.add(mock(OfferDtoResponseNoPdf.class)) ;
        doReturn(offerDtoResponseList).when(companyService).getOffersByCompany( 1L) ;

        //Act & Assert
        mockMvc.perform(get("/companies/{id}/offers", 1)).
                andExpect(status().isOk()) ;
    }

    @Test
    @WithMockUser
    void getOffersNoCompany() throws Exception {
        //Arrange
        List<OfferDtoResponse> offerDtoResponseList = new ArrayList<>() ;
        offerDtoResponseList.add(mock(OfferDtoResponse.class)) ;
        offerDtoResponseList.add(mock(OfferDtoResponse.class)) ;

        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(companyService).getOffersByCompany(1L) ;

        //Act & Assert
        mockMvc.perform(get("/companies/{id}/offers", 1)).
                andExpect(status().isNotFound()) ;
    }

    @Test
    @WithMockUser(authorities = {"COMPANY"})
    void postOfferHappyDay() throws Exception {
        //Arrange
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        OfferDto offerDto = new OfferDto("test", 1, "2002-12-12", "2002-12-14") ;

        //Act & Assert
        mockMvc.perform(multipart("/companies/{id}/offers", 1)
                        .file(mockMultipartFile)
                        .param("offerDto", asJsonString(offerDto)))
                .andExpect(status().isAccepted()) ;
    }

    @Test
    @WithMockUser(authorities = {"COMPANY"})
    void postOfferNoCompany() throws Exception {
        //Arrange
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        OfferDto offerDto = new OfferDto("test", 1, "2002-12-12", "2002-12-14") ;
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(companyService).addOffer(eq(1L), any(OfferDto.class), any(MultipartFile.class));

        //Act & Assert
        mockMvc.perform(multipart("/companies/{id}/offers", 1)
                        .file(mockMultipartFile)
                        .param("offerDto", asJsonString(offerDto)))
                .andExpect(status().isNotFound()) ;
    }


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
