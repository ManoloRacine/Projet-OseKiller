package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.controller.payload.response.NameAndEmailDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.model.ERole;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.Role;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.service.implementation.OfferServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class OfferServiceTest {

    @Mock
    OfferRepository offerRepository;

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    OfferServiceImpl offerService;

    static Company company;
    static Offer offer;
    static Student mockStudent1;
    static Student mockStudent2;
    static Student mockStudent3;
    @BeforeAll
    static void setupAll(){
        mockStudent1 = new Student("Joe Biden","jbiden@osk.com","encrypted-pass");
        mockStudent1.setRole(new Role(ERole.STUDENT.name()));
        mockStudent1.setId(1L);
        mockStudent2 = new Student("Obama Barrack","obarrackn@osk.com","encrypted-pass");
        mockStudent2.setRole(new Role(ERole.STUDENT.name()));
        mockStudent2.setId(2L);
        mockStudent3 = new Student("Trump donald","tdonald@osk.com","encrypted-pass");
        mockStudent3.setRole(new Role(ERole.STUDENT.name()));
        mockStudent3.setId(3L);

        company = mock(Company.class);
        offer = new Offer(company, "Junior dev", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
    }

    @BeforeEach
    void setupEach(){
        offer.setApplicants(new ArrayList<>());
    }

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
        when(offerRepository.findAllByAcceptedIsFalseAndFeedbackIsNull()).thenReturn(mockList) ;

        List<GeneralOfferDto> list = offerService.getAllInvalidOffers() ;

        assertNotNull(list);
        assertSame(list.size(), 3);
        assertEquals(list, mockDtoList);
    }

    @Test
    void getInvalidOffersEmpty() {
        List<GeneralOfferDto> list = offerService.getAllValidOffers() ;

        assertNotNull(list);
        assertSame(list.size(), 0);
    }

    @Test
    void addApplicantToOfferHappyDay(){
        //Arrange

        when(offerRepository.findByIdAndFetchApplicants(anyLong())).thenReturn(Optional.of(offer));
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(mockStudent1));

        //Act

        offerService.addApplicantToOffer(1L,2L);

        //Assert

        assertThat(offer.getApplicants().size()).isEqualTo(1);
        assertThat(offer.getApplicants()).contains(mockStudent1);
        verify(offerRepository).save(offer);

    }
    @Test
    void addApplicantToOfferAlreadyApplied(){
        //Arrange

        offer.getApplicants().add(mockStudent1);
        when(offerRepository.findByIdAndFetchApplicants(anyLong())).thenReturn(Optional.of(offer));
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(mockStudent1));

        //Act & Assert

        assertThatThrownBy(() -> offerService.addApplicantToOffer(1L,2L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.CONFLICT);
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

        //Act & Assert

        assertThatThrownBy(() -> offerService.addApplicantToOffer(1L,2L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    void getApplicantsOfferNotFound() {

        //Act & Assert

        assertThatThrownBy(() -> offerService.getApplicants(1))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getApplicantsHappyDay() {

        //Arrange

        offer.setApplicants(List.of(mockStudent1,mockStudent2,mockStudent3));

        when(offerRepository.findByIdAndFetchApplicants(anyLong())).thenReturn(Optional.of(offer));

        List<NameAndEmailDto> expected = offer.getApplicants().stream()
                .map(applicant ->
                        new NameAndEmailDto(applicant.getName(),applicant.getEmail())
                ).toList();

        //Act

        List<NameAndEmailDto> actual = offerService.getApplicants(1);

        //Assert

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expected);
    }
}
