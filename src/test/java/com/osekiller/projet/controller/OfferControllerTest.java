package com.osekiller.projet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.controller.payload.response.OfferDtoResponseNoPdf;
import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.service.implementation.CompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OfferControllerTest {
    @MockBean
    private CompanyServiceImpl companyService ;

    @Autowired
    private MockMvc mockMvc ;

    @Test
    @WithMockUser
    void getAllValidOffersHappyDay() throws Exception {
        GeneralOfferDto generalOfferDto1 = new GeneralOfferDto(1L, 1L, "google", "dev",
                20.50, "2022-10-22", "2022-10-23") ;
        GeneralOfferDto generalOfferDto2 = new GeneralOfferDto(2L, 2L, "google", "dev",
                20.50, "2022-10-22", "2022-10-23") ;
        GeneralOfferDto generalOfferDto3 = new GeneralOfferDto(3L, 3L, "google", "dev",
                20.50, "2022-10-22", "2022-10-23") ;
        List<GeneralOfferDto> generalOfferDtos = new ArrayList<>()  ;
        generalOfferDtos.add(generalOfferDto1);
        generalOfferDtos.add(generalOfferDto2);
        generalOfferDtos.add(generalOfferDto3);
        when(companyService.getAllValidOffers()).thenReturn(generalOfferDtos) ;

        mockMvc.perform(get("/offers?accepted=true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].offerId", is(1)))
                .andExpect(jsonPath("$[1].offerId", is(2)))
                .andExpect(jsonPath("$[2].offerId", is(3))) ;
    }

    @Test
    @WithMockUser
    void getAllValidOffersEmpty() throws Exception {
        List<GeneralOfferDto> generalOfferDtos = new ArrayList<>()  ;
        when(companyService.getAllValidOffers()).thenReturn(generalOfferDtos) ;

        mockMvc.perform(get("/offers?accepted=true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]")) ;
    }

    @Test
    @WithMockUser
    void getAllInvalidOffersHappyDay() throws Exception {
        GeneralOfferDto generalOfferDto1 = new GeneralOfferDto(1L, 1L, "google", "dev",
                20.50, "2022-10-22", "2022-10-23") ;
        GeneralOfferDto generalOfferDto2 = new GeneralOfferDto(2L, 2L, "google", "dev",
                20.50, "2022-10-22", "2022-10-23") ;
        GeneralOfferDto generalOfferDto3 = new GeneralOfferDto(3L, 3L, "google", "dev",
                20.50, "2022-10-22", "2022-10-23") ;
        List<GeneralOfferDto> generalOfferDtos = new ArrayList<>()  ;
        generalOfferDtos.add(generalOfferDto1);
        generalOfferDtos.add(generalOfferDto2);
        generalOfferDtos.add(generalOfferDto3);
        when(companyService.getAllInvalidOffers()).thenReturn(generalOfferDtos) ;

        mockMvc.perform(get("/offers?accepted=false"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].offerId", is(1)))
                .andExpect(jsonPath("$[1].offerId", is(2)))
                .andExpect(jsonPath("$[2].offerId", is(3))) ;
    }

    @Test
    @WithMockUser
    void getAllInvalidOffersEmpty() throws Exception {
        List<GeneralOfferDto> generalOfferDtos = new ArrayList<>()  ;
        when(companyService.getAllInvalidOffers()).thenReturn(generalOfferDtos) ;

        mockMvc.perform(get("/offers?accepted=false"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]")) ;
    }
}
