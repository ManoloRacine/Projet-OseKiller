package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponseNoPdf;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.repository.user.CompanyRepository;
import com.osekiller.projet.service.implementation.CompanyServiceImpl;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Test
    void addOfferHappyDay() {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        OfferDto offerDto = new OfferDto("test", 1, "2002-12-12", "2002-12-14") ;
        Company company = mock(Company.class) ;
        when(companyRepository.findById(any())).thenReturn(Optional.of(company)) ;

        companyService.addOffer(1L, offerDto, mockMultipartFile);

        verify(offerRepository).save(any()) ;
    }

    @Test
    void addOfferCompanyNonExistent() {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        OfferDto offerDto = new OfferDto("test", 1, "2002-12-12", "2002-12-14") ;
        Company company = mock(Company.class) ;
        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty()) ;


        AssertionsForClassTypes.assertThatThrownBy(() -> companyService.addOffer(1L, offerDto, mockMultipartFile))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getOffersHappyDay() throws MalformedURLException {
        Offer offer1 = new Offer(mock(Company.class), "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        Offer offer2 = new Offer(mock(Company.class), "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        Offer offer3 = new Offer(mock(Company.class), "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        offer1.setId(1L);
        offer2.setId(2L);
        offer3.setId(3L);
        when(cvPath.resolve("1.pdf")).thenReturn(Path.of("1.pdf")) ;
        when(cvPath.resolve("2.pdf")).thenReturn(Path.of("2.pdf")) ;
        when(cvPath.resolve("3.pdf")).thenReturn(Path.of("3.pdf")) ;
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

        List<OfferDtoResponseNoPdf> list = companyService.getOffersByCompany(1L) ;

        assertNotNull(list);
        assertSame(list.size(), 3);
        assertEquals(list, mockDtoList);
    }

    @Test
    void getOffersEmpty() {
        Company company = mock(Company.class) ;
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company)) ;
        when(offerRepository.findAllByOwner(company)).thenReturn(new ArrayList<>()) ;

        List<OfferDtoResponseNoPdf> list = companyService.getOffersByCompany(1L) ;

        assertNotNull(list);
        assertSame(list.size(), 0);
    }

    @Test
    void getOffersNonExistentCompany() {
        when(offerRepository.findAllByOwner(mock(Company.class))).thenReturn(new ArrayList<>()) ;

        assertThatThrownBy(() -> companyService.getOffersByCompany( 1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }
}
