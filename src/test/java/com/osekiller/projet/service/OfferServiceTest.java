package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.service.implementation.OfferServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OfferServiceTest {

    @Mock
    OfferRepository offerRepository ;

    @InjectMocks
    OfferServiceImpl offerService;

    @Test
    void getOfferHappyDay() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        Offer offer = new Offer(mock(Company.class), "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        offer.setId(1L);
        offer.setPdf(mockMultipartFile.getBytes());
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer)) ;

        assertEquals(offerService.getOffer(1L), new OfferDtoResponse(1L, "test", 1, "2002-12-14", "2002-12-16", new ByteArrayResource(mockMultipartFile.getBytes())));
    }

    @Test
    void getOfferNonExistent() {
        when(offerRepository.findById(anyLong())).thenReturn(Optional.empty()) ;

        assertThatThrownBy(() -> offerService.getOffer(1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
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

        List<GeneralOfferDto> list = offerService.getAllValidOffers() ;

        assertNotNull(list);
        assertSame(list.size(), 3);
        assertEquals(list, mockDtoList);
    }

    @Test
    void getValidOffersEmpty() {
        when(offerRepository.findAllByAcceptedIsTrue()).thenReturn(new ArrayList<>()) ;

        List<GeneralOfferDto> list = offerService.getAllValidOffers() ;

        assertNotNull(list);
        assertSame(list.size(), 0);
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

        List<GeneralOfferDto> list = offerService.getAllInvalidOffers() ;

        assertNotNull(list);
        assertSame(list.size(), 3);
        assertEquals(list, mockDtoList);
    }

    @Test
    void getInvalidOffersEmpty() {
        when(offerRepository.findAllByAcceptedIsFalse()).thenReturn(new ArrayList<>()) ;

        List<GeneralOfferDto> list = offerService.getAllValidOffers() ;

        assertNotNull(list);
        assertSame(list.size(), 0);
    }

    @Test
    void addApplicantToOfferHappyDay(){
        //Arrange

        //Act

        //Assert

    }
    @Test
    void addApplicantToOfferAlreadyApplied(){
        //Arrange

        //Act

        //Assert

    }

    @Test
    void addApplicantToOfferNotFound(){

        //Act & Assert

        assertThatThrownBy(() -> offerService.addApplicantToOffer(1L,2L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void addApplicantNotFoundToOffer(){
        //Arrange

        //Act

        //Assert

    }

}
