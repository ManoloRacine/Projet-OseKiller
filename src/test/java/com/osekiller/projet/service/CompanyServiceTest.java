package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.controller.payload.response.OfferDtoResponseNoPdf;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.repository.user.CompanyRepository;
import com.osekiller.projet.service.implementation.CompanyServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CompanyServiceTest {

    @Mock
    CompanyRepository companyRepository ;

    @Mock
    OfferRepository offerRepository ;

    @Mock
    Path cvPath ;

    @InjectMocks
    CompanyServiceImpl companyService ;

    static Company company;

    static Offer offer;

    @BeforeAll
    static void setup() {
        company = mock(Company.class);
        offer = new Offer(company,"Junior Web Dev",25.5,LocalDate.now(),LocalDate.now().plusDays(20L));
        offer.setId(1L);
    }

    @Test
    void addOfferHappyDay() {

        //Arrange

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        OfferDto offerDto = new OfferDto("test", 1, "2002-12-12", "2002-12-14") ;
        Company company = mock(Company.class) ;
        when(companyRepository.findById(any())).thenReturn(Optional.of(company)) ;

        companyService.addOffer(1L, offerDto, mockMultipartFile);

        verify(offerRepository).save(any()) ;
    }

    @Test
    void addOfferCompanyNonExistent() {

        //Arrange

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        OfferDto offerDto = new OfferDto("test", 1, "2002-12-12", "2002-12-14") ;

        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty()) ;

        //Act & Assert

        assertThatThrownBy(() -> companyService.addOffer(1L, offerDto, mockMultipartFile))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getOfferHappyDay() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        Offer offer = new Offer(mock(Company.class), "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        offer.setId(1L);
        offer.setPdf(mockMultipartFile.getBytes());
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer)) ;

        Assertions.assertEquals(companyService.getOffer(1L), new OfferDtoResponse(1L, "test", 1, "2002-12-14", "2002-12-16", new ByteArrayResource(mockMultipartFile.getBytes())));
    }

    @Test
    void getOfferNonExistent() {

        //Arrange

        when(offerRepository.findById(anyLong())).thenReturn(Optional.empty()) ;

        //Act & Assert

        assertThatThrownBy(() -> companyService.getOffer(1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getOffersHappyDay() {

        //Arrange

        Offer offer1 = new Offer(mock(Company.class), "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        Offer offer2 = new Offer(mock(Company.class), "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        Offer offer3 = new Offer(mock(Company.class), "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        offer1.setId(1L);
        offer2.setId(2L);
        offer3.setId(3L);
        when(cvPath.resolve("1.pdf")).thenReturn(Path.of("1.pdf")) ;
        when(cvPath.resolve("2.pdf")).thenReturn(Path.of("1.pdf")) ;
        when(cvPath.resolve("3.pdf")).thenReturn(Path.of("1.pdf")) ;
        OfferDtoResponseNoPdf offerDto1 = new OfferDtoResponseNoPdf(offer1.getId(), offer1.getPosition(), offer1.getSalary(), offer1.getStartDate().toString(), offer1.getEndDate().toString()) ;
        OfferDtoResponseNoPdf offerDto2 = new OfferDtoResponseNoPdf(offer2.getId(), offer2.getPosition(), offer2.getSalary(), offer2.getStartDate().toString(), offer2.getEndDate().toString()) ;
        OfferDtoResponseNoPdf offerDto3 = new OfferDtoResponseNoPdf(offer3.getId(), offer3.getPosition(), offer3.getSalary(), offer3.getStartDate().toString(), offer3.getEndDate().toString()) ;
        List<Offer> mockList = new ArrayList<>() ;
        mockList.add(offer1) ;
        mockList.add(offer2) ;
        mockList.add(offer3) ;
        List<OfferDtoResponseNoPdf> mockDtoList = new ArrayList<>() ;
        mockDtoList.add(offerDto1) ;
        mockDtoList.add(offerDto2) ;
        mockDtoList.add(offerDto3) ;
        Company company = mock(Company.class) ;
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company)) ;
        when(offerRepository.findAllByOwner(company)).thenReturn(mockList) ;

        //Act

        List<OfferDtoResponseNoPdf> list = companyService.getAllOffersCompany(1L) ;

        //Assert

        assertNotNull(list);
        assertSame(list.size(), 3);
        assertEquals(list, mockDtoList);
    }

    @Test
    void getOffersEmpty() {
        //Arrange

        Company company = mock(Company.class) ;
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company)) ;
        when(offerRepository.findAllByOwner(company)).thenReturn(new ArrayList<>()) ;

        //Act

        List<OfferDtoResponseNoPdf> list = companyService.getAllOffersCompany(1L) ;

        //Assert

        assertNotNull(list);
        assertSame(list.size(), 0);
    }

    @Test
    void getOffersNonExistentCompany() {
        //Arrange

        when(offerRepository.findAllByOwner(mock(Company.class))).thenReturn(new ArrayList<>()) ;

        //Act & Assert

        assertThatThrownBy(() -> companyService.getAllOffersCompany( 1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @WithMockUser
    void validateOfferHappyDay(){
        //Arrange

        String feedback = "This is the best offer of all time";

        //Act

        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer));
        companyService.validateOffer(1L, feedback);

        //Assert

        assertThat(offer.getAccepted())
                .isEqualTo(true);
        verify(offerRepository).save(offer);
    }

    @Test
    void validateOfferNonExistent(){
        //Act & Assert

        assertThatThrownBy(() -> companyService.validateOffer(1L, "feedback"))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void invalidateOfferHappyDay(){
        //Arrange

        String feedback = "This is the worst offer of all time";

        //Act

        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer));
        companyService.invalidateOffer(1L, feedback);

        //Assert

        assertThat(offer.getFeedback()).isEqualTo(feedback);
        verify(offerRepository).save(offer);
    }

    @Test
    void invalidateOfferNonExistent(){

        //Act & Assert

        assertThatThrownBy(() -> companyService.invalidateOffer(1L, "feedback"))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status")
                .isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    void invalidateOfferNoFeedback(){

        //Act & Assert

        assertThatThrownBy(() -> companyService.invalidateOffer(1L, null))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status")
                .isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    void invalidateOfferEmptyFeedback(){

        //Act & Assert

        assertThatThrownBy(() -> companyService.invalidateOffer(1L, ""))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status")
                .isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    void invalidateOfferBlankFeedback(){

        //Act & Assert

        assertThatThrownBy(() -> companyService.invalidateOffer(1L, "       "))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status")
                .isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    void getValidOffers() {
        Company company = mock(Company.class);
        Offer offer1 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        Offer offer2 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        Offer offer3 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        offer1.setId(1L);
        offer2.setId(2L);
        offer3.setId(3L);
        when(company.getName()).thenReturn("google");
        when(company.getId()).thenReturn(1L) ;
        GeneralOfferDto offerDto1 = new GeneralOfferDto(offer1.getId(), 1L, "google", offer1.getPosition(), offer1.getSalary(), offer1.getStartDate().toString(), offer1.getEndDate().toString()) ;
        GeneralOfferDto offerDto2 = new GeneralOfferDto(offer2.getId(),1L, "google", offer2.getPosition(), offer2.getSalary(), offer2.getStartDate().toString(), offer2.getEndDate().toString()) ;
        GeneralOfferDto offerDto3 = new GeneralOfferDto(offer3.getId(),1L, "google", offer3.getPosition(), offer3.getSalary(), offer3.getStartDate().toString(), offer3.getEndDate().toString()) ;
        List<Offer> mockList = new ArrayList<>() ;
        mockList.add(offer1) ;
        mockList.add(offer2) ;
        mockList.add(offer3) ;
        List<GeneralOfferDto> mockDtoList = new ArrayList<>() ;
        mockDtoList.add(offerDto1) ;
        mockDtoList.add(offerDto2) ;
        mockDtoList.add(offerDto3) ;
        when(offerRepository.findAllByAcceptedIsTrue()).thenReturn(mockList) ;

        List<GeneralOfferDto> list = companyService.getAllValidOffers() ;

        assertNotNull(list);
        assertSame(list.size(), 3);
        assertEquals(list, mockDtoList);
    }

    @Test
    void getValidOffersEmpty() {
        when(offerRepository.findAllByAcceptedIsTrue()).thenReturn(new ArrayList<>()) ;

        List<GeneralOfferDto> list = companyService.getAllValidOffers() ;

        Assertions.assertNotNull(list);
        Assertions.assertSame(list.size(), 0);
    }

    @Test
    void getInvalidOffers() {
        Company company = mock(Company.class);
        Offer offer1 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        Offer offer2 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        Offer offer3 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        offer1.setId(1L);
        offer2.setId(2L);
        offer3.setId(3L);
        when(company.getName()).thenReturn("google");
        when(company.getId()).thenReturn(1L) ;
        GeneralOfferDto offerDto1 = new GeneralOfferDto(offer1.getId(), 1L, "google", offer1.getPosition(), offer1.getSalary(), offer1.getStartDate().toString(), offer1.getEndDate().toString()) ;
        GeneralOfferDto offerDto2 = new GeneralOfferDto(offer2.getId(),1L, "google", offer2.getPosition(), offer2.getSalary(), offer2.getStartDate().toString(), offer2.getEndDate().toString()) ;
        GeneralOfferDto offerDto3 = new GeneralOfferDto(offer3.getId(),1L, "google", offer3.getPosition(), offer3.getSalary(), offer3.getStartDate().toString(), offer3.getEndDate().toString()) ;
        List<Offer> mockList = new ArrayList<>() ;
        mockList.add(offer1) ;
        mockList.add(offer2) ;
        mockList.add(offer3) ;
        List<GeneralOfferDto> mockDtoList = new ArrayList<>() ;
        mockDtoList.add(offerDto1) ;
        mockDtoList.add(offerDto2) ;
        mockDtoList.add(offerDto3) ;
        when(offerRepository.findAllByAcceptedIsFalse()).thenReturn(mockList) ;

        List<GeneralOfferDto> list = companyService.getAllInvalidOffers() ;

        assertNotNull(list);
        assertSame(list.size(), 3);
        assertEquals(list, mockDtoList);
    }

    @Test
    void getInvalidOffersEmpty() {
        when(offerRepository.findAllByAcceptedIsFalse()).thenReturn(new ArrayList<>()) ;

        List<GeneralOfferDto> list = companyService.getAllValidOffers() ;

        assertNotNull(list);
        assertSame(list.size(), 0);
    }
}
