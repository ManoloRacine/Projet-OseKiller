package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.response.InternDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponseNoPdf;
import com.osekiller.projet.model.Contract;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.ContractRepository;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.repository.user.CompanyRepository;
import com.osekiller.projet.service.implementation.CompanyServiceImpl;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

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

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CompanyServiceTest {

    @Mock
    CompanyRepository companyRepository ;

    @Mock
    OfferRepository offerRepository ;

    @Mock
    ContractRepository contractRepository ;

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
    void getOffersHappyDay() {

        //Arrange

        Offer offer1 = new Offer(mock(Company.class), "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        Offer offer2 = new Offer(mock(Company.class), "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        Offer offer3 = new Offer(mock(Company.class), "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        offer1.setId(1L);
        offer2.setId(2L);
        offer3.setId(3L);
        OfferDtoResponseNoPdf offerDto1 = new OfferDtoResponseNoPdf(offer1.getId(), offer1.getPosition(), offer1.getSalary(), offer1.getStartDate().toString(), offer1.getEndDate().toString(), false, null) ;
        OfferDtoResponseNoPdf offerDto2 = new OfferDtoResponseNoPdf(offer2.getId(), offer2.getPosition(), offer2.getSalary(), offer2.getStartDate().toString(), offer2.getEndDate().toString(), false, null) ;
        OfferDtoResponseNoPdf offerDto3 = new OfferDtoResponseNoPdf(offer3.getId(), offer3.getPosition(), offer3.getSalary(), offer3.getStartDate().toString(), offer3.getEndDate().toString(),false, null) ;
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

        List<OfferDtoResponseNoPdf> list = companyService.getOffersByCompany(1L) ;

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

        List<OfferDtoResponseNoPdf> list = companyService.getOffersByCompany(1L) ;

        //Assert

        assertNotNull(list);
        assertSame(list.size(), 0);
    }

    @Test
    void getOffersNonExistentCompany() {

        //Act & Assert

        assertThatThrownBy(() -> companyService.getOffersByCompany( 1L))
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

        assertThat(offer.isAccepted())
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
    void getInternsHappyDay() {
        //Arrange
        List<Contract> contracts = new ArrayList<>() ;
        for (int i = 0; i < 5; i++) {
            Contract contract1 = mock(Contract.class) ;
            when(contract1.getStudent()).thenReturn(mock(Student.class)) ;
            when(contract1.getOffer()).thenReturn(mock(Offer.class)) ;
            when(contract1.getOffer().getOwner()).thenReturn(mock(Company.class)) ;
            contracts.add(contract1);
        }
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(mock(Company.class))) ;
        when(contractRepository.findAllByOffer_Owner_Id(anyLong())).thenReturn(contracts) ;

        //Act
        List<InternDto> dtos = companyService.getInterns(1L) ;

        //Assert
        assertNotNull(dtos);
        assertSame(dtos.size(), 5);
    }

    @Test
    void getInternsEmpty() {
        //Arrange
        List<Contract> contracts = new ArrayList<>() ;
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(mock(Company.class))) ;
        when(contractRepository.findAllByOffer_Owner_Id(anyLong())).thenReturn(contracts) ;

        //Act
        List<InternDto> dtos = companyService.getInterns(1L) ;

        //Assert
        assertNotNull(dtos);
        assertSame(dtos.size(), 0);
    }

    @Test
    void getInternsNoCompany() {
        //Arrange
        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty()) ;

        //Act && Assert
        AssertionsForClassTypes.assertThatThrownBy(() -> companyService.getInterns(1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }
}
