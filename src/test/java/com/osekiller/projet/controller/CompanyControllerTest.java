package com.osekiller.projet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.request.ValidationDto;
import com.osekiller.projet.controller.payload.response.UserInfoDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.controller.payload.response.OfferDtoResponseNoPdf;
import com.osekiller.projet.model.ERole;
import com.osekiller.projet.model.Role;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.service.OfferService;
import com.osekiller.projet.service.implementation.CompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @WithMockUser(authorities = {"COMPANY"})
    void getOfferHappyDay() throws Exception {
        //Arrange
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        OfferDtoResponse offerDtoResponse = new OfferDtoResponse(1L, "test", 1, "2002-12-12", "2002-12-14", new InputStreamResource(mockMultipartFile.getInputStream()),true,"WOW") ;
        doReturn(offerDtoResponse).when(offerService).getOffer(1L);
        when(companyService.companyExists(anyLong())).thenReturn(true);
        when(companyService.companyOwnsOffer(anyLong(),anyLong())).thenReturn(true);

        //Act & Assert
        mockMvc.perform(get("/companies/{companyId}/offers/{offerId}", 1, 1)).
                andExpect(status().isOk()) ;
    }

    @Test
    @WithMockUser(authorities = {"COMPANY"})
    void getOfferNoCompany() throws Exception {
        //Arrange
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(offerService).getOffer(1L);

        //Act & Assert
        mockMvc.perform(get("/companies/{companyId}/offers/{offerId}", 1, 1)).
                andExpect(status().isNotFound()) ;
    }

    @Test
    @WithMockUser(authorities = {"COMPANY"})
    void getOfferPdfHappyDay() throws Exception {
        //Arrange
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        OfferDtoResponse offerDtoResponse = new OfferDtoResponse(1L, "test", 1, "2002-12-12", "2002-12-14", new InputStreamResource(mockMultipartFile.getInputStream()),true,"WOW") ;
        doReturn(offerDtoResponse).when(offerService).getOffer(1L) ;
        doReturn(true).when(companyService).companyExists(anyLong()) ;
        doReturn(true).when(companyService).companyOwnsOffer(anyLong(), anyLong()) ;

        //Act & Assert
        mockMvc.perform(get("/companies/{companyId}/offers/{offerId}/pdf", 1, 1)).
                andExpect(status().isOk()) ;
    }

    @Test
    @WithMockUser
    void getOfferPdfNoCompany() throws Exception {
        //Arrange
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(offerService).getOffer(1L);

        //Act & Assert
        mockMvc.perform(get("/companies/{companyId}/offers/{offerId}/pdf", 1, 1)).
                andExpect(status().isNotFound()) ;
    }

    @Test
    @WithMockUser(authorities = {"MANAGER"})
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
    @WithMockUser(authorities = {"COMPANY"})
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
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(offerService).addOffer(eq(1L), any(OfferDto.class), any(MultipartFile.class));

        //Act & Assert
        mockMvc.perform(multipart("/companies/{id}/offers", 1)
                        .file(mockMultipartFile)
                        .param("offerDto", asJsonString(offerDto)))
                .andExpect(status().isNotFound()) ;
    }
    @Test
    @WithMockUser(authorities = {"MANAGER"})
    void validateOfferHappyFeedback() throws Exception {

        //Arrange

        ValidationDto dto = new ValidationDto(true, "One of the offers of all time");

        when(companyService.companyExists(anyLong())).thenReturn(true);
        when(companyService.companyOwnsOffer(anyLong(),anyLong())).thenReturn(true);

        //Act & Assert

        mockMvc.perform(post("/companies/1/offers/2/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto))).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"MANAGER"})
    void validateAndInvalidateOfferBadRequestValidation() throws Exception {
        //Arrange

        ValidationDto dto = new ValidationDto(null, null);

        //Act & Assert

        mockMvc.perform(post("/companies/1/offers/2/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = {"MANAGER"})
    void validateAndInvalidateOfferNotFound() throws Exception {
        //Arrange

        ValidationDto dto = new ValidationDto(true, "One of the offers of all time");

        //Act & Assert

        mockMvc.perform(post("/companies/1/offers/2/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isNotFound());
    }
    @Test
    @WithMockUser(authorities = {"MANAGER"})
    void invalidateOfferHappyDay() throws Exception {
        //Arrange

        ValidationDto dto = new ValidationDto(false, "One of the offers of all time");

        when(companyService.companyExists(anyLong())).thenReturn(true);
        when(companyService.companyOwnsOffer(anyLong(),anyLong())).thenReturn(true);

        //Act & Assert

        mockMvc.perform(post("/companies/1/offers/2/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto))).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"MANAGER"})
    void getOfferApplicantsOfferNotFound() throws Exception {
        //Act & Assert

        mockMvc.perform(get("/companies/1/offers/2/applicants"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = {"COMPANY"})
    void getOfferApplicantsHappyDay() throws Exception {

        //Arrange

        Student mockStudent1 = new Student("Joe Biden","jbiden@osk.com","encrypted-pass");
        mockStudent1.setRole(new Role(ERole.STUDENT.name()));
        mockStudent1.setId(1L);
        Student mockStudent2 = new Student("Obama Barrack","obarrackn@osk.com","encrypted-pass");
        mockStudent2.setRole(new Role(ERole.STUDENT.name()));
        mockStudent2.setId(2L);
        Student mockStudent3 = new Student("Trump donald","tdonald@osk.com","encrypted-pass");
        mockStudent3.setRole(new Role(ERole.STUDENT.name()));
        mockStudent3.setId(3L);

        List<UserInfoDto> dtoList = Stream.of(mockStudent1,mockStudent2,mockStudent3)
                .map(applicant -> new UserInfoDto(applicant.getId(), applicant.getName(), applicant.getEmail(), false)).toList();

        when(companyService.companyExists(anyLong())).thenReturn(true);
        when(companyService.companyOwnsOffer(anyLong(),anyLong())).thenReturn(true);
        when(offerService.getApplicants(anyLong())).thenReturn(dtoList);

        String expected = asJsonString(dtoList);

        //Act & Assert

        MvcResult result = mockMvc.perform(get("/companies/1/offers/2/applicants"))
                .andExpect(status().isOk())
                .andReturn();

        String actual = result.getResponse().getContentAsString();

        assertThat(actual).isNotBlank().isEqualTo(expected);
    }

    @Test
    @WithMockUser(authorities = {"COMPANY"})
    void putOfferCompanyNotFound() throws Exception {
        //Arrange
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        OfferDto offerDto = new OfferDto("test", 1, "2002-12-12", "2002-12-14") ;
        when(companyService.companyExists(anyLong())).thenReturn(false);

        //Act & Assert
        mockMvc.perform(multipart(HttpMethod.PUT, "/companies/1/offers/2")
                        .file(mockMultipartFile)
                        .param("offerDto", asJsonString(offerDto))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isNotFound()) ;
    }
    @Test
    @WithMockUser(authorities = {"COMPANY"})
    void putOfferOfferNotFound() throws Exception {
        //Arrange
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        OfferDto offerDto = new OfferDto("test", 1, "2002-12-12", "2002-12-14") ;
        when(companyService.companyExists(anyLong())).thenReturn(true);
        when(companyService.companyOwnsOffer(anyLong(),anyLong())).thenReturn(false);

        //Act & Assert
        mockMvc.perform(multipart(HttpMethod.PUT, "/companies/1/offers/2")
                        .file(mockMultipartFile)
                        .param("offerDto", asJsonString(offerDto))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isNotFound()) ;
    }

    @Test
    @WithMockUser(authorities = {"COMPANY"})
    void putOfferHappyDay() throws Exception {
        //Arrange
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        OfferDto offerDto = new OfferDto("test", 1, "2002-12-12", "2002-12-14") ;
        when(companyService.companyExists(anyLong())).thenReturn(true);
        when(companyService.companyOwnsOffer(anyLong(),anyLong())).thenReturn(true);

        //Act & Assert
        mockMvc.perform(multipart(HttpMethod.PUT, "/companies/1/offers/2")
                        .file(mockMultipartFile)
                        .param("offerDto", asJsonString(offerDto))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        verify(offerService).modifyOffer(anyLong(),any(OfferDto.class),any(MultipartFile.class));
    }

    @Test
    @WithMockUser(authorities = {"COMPANY"})
    void acceptApplicantHappyDay() throws Exception {
        when(companyService.companyExists(anyLong())).thenReturn(true);
        when(companyService.companyOwnsOffer(anyLong(),anyLong())).thenReturn(true);

        //Act & Assert

        mockMvc.perform(post("/companies/1/offers/2/applicants/3/accept"))
                .andExpect(status().isOk());
    }


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
