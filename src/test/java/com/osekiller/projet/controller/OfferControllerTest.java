package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.controller.payload.response.UserDto;
import com.osekiller.projet.service.AuthService;
import com.osekiller.projet.service.OfferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OfferControllerTest {
    @MockBean
    private OfferService offerService;

    @MockBean
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc ;

    @Test
    @WithMockUser
    void getAllValidOffersHappyDay() throws Exception {
        //Arrange
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
        when(offerService.getAllValidOffers()).thenReturn(generalOfferDtos) ;

        //Act & Assert
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
        //Arrange
        List<GeneralOfferDto> generalOfferDtos = new ArrayList<>()  ;
        when(offerService.getAllValidOffers()).thenReturn(generalOfferDtos) ;

        //Act & Assert
        mockMvc.perform(get("/offers?accepted=true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]")) ;
    }

    @Test
    @WithMockUser(authorities = {"MANAGER"})
    void getAllInvalidOffersHappyDay() throws Exception {
        //Arrange
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
        when(offerService.getAllInvalidOffers()).thenReturn(generalOfferDtos) ;

        //Act & Assert
        mockMvc.perform(get("/offers?accepted=false"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].offerId", is(1)))
                .andExpect(jsonPath("$[1].offerId", is(2)))
                .andExpect(jsonPath("$[2].offerId", is(3))) ;
    }

    @Test
    @WithMockUser(authorities = {"MANAGER"})
    void getAllInvalidOffersEmpty() throws Exception {
        //Arrange
        List<GeneralOfferDto> generalOfferDtos = new ArrayList<>()  ;
        when(offerService.getAllInvalidOffers()).thenReturn(generalOfferDtos) ;

        //Act & Assert
        mockMvc.perform(get("/offers?accepted=false"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]")) ;
    }

    @Test
    @WithMockUser(authorities = {"STUDENT"})
    void applyToInternshipOfferHappyDay() throws Exception {
        //Arrange

        when(authService.getUserFromToken(anyString())).thenReturn(mock(UserDto.class));

        //Act & Assert

        mockMvc.perform(post("/offers/1/apply").header(HttpHeaders.AUTHORIZATION,"student-jwt"))
                .andExpect(status().isOk());
    }
}
