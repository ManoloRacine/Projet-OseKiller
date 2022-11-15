package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.response.ApplicationDto;
import com.osekiller.projet.controller.payload.response.ContractDto;
import com.osekiller.projet.controller.payload.response.ContractToEvaluateDto;
import com.osekiller.projet.controller.payload.response.EvaluationSimpleDto;
import com.osekiller.projet.model.Contract;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.model.user.Manager;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.ContractRepository;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.service.implementation.ContractServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ContractServiceTest {
    @Mock
    ContractRepository contractRepository ;

    @Mock
    OfferRepository offerRepository ;

    @InjectMocks
    ContractServiceImpl contractService;

    @Test
    void getContractsHappyDay() {
        //Arrange
        List<Contract> contracts = new ArrayList<>() ;
        for (int i = 0; i < 5; i++) {
            Contract contract1 = mock(Contract.class) ;
            when(contract1.getStudent()).thenReturn(mock(Student.class)) ;
            when(contract1.getManager()).thenReturn(mock(Manager.class)) ;
            when(contract1.getOffer()).thenReturn(mock(Offer.class)) ;
            when(contract1.getOffer().getOwner()).thenReturn(mock(Company.class)) ;
            contracts.add(contract1);
        }
        when(contractRepository.findAll()).thenReturn(contracts) ;

        //Act
        List<ContractDto> contractDtos = contractService.getContracts() ;

        //Assert
        assertNotNull(contractDtos);
        assertSame(contractDtos.size(), 5);

    }

    @Test
    void getContractsEmpty() {
        //Arrange
        List<Contract> contracts = new ArrayList<>() ;
        when(contractRepository.findAll()).thenReturn(contracts) ;

        //Act
        List<ContractDto> contractDtos = contractService.getContracts() ;

        //Assert
        assertNotNull(contractDtos);
        assertSame(contractDtos.size(), 0);

    }

    @Test
    void getApplicationsHappyDay() {
        //Arrange
        List<Offer> offers = new ArrayList<>() ;
        for (int i = 0; i < 5; i++) {
            Offer offer = mock(Offer.class) ;
            when(offer.getOwner()).thenReturn(mock(Company.class)) ;
            List<Student> list = List.of(mock(Student.class), mock(Student.class), mock(Student.class)) ;
            when(offer.getAcceptedApplicants()).thenReturn(list) ;
            offers.add(offer);
        }
        when(offerRepository.findAllByHasAcceptedApplicants()).thenReturn(offers) ;

        //Act
        List<ApplicationDto> applicationDtos = contractService.getAcceptedApplications() ;

        //Assert
        assertNotNull(applicationDtos);
        assertSame(applicationDtos.size(), 15);

    }

    @Test
    void getApplicationsEmpty() {
        //Arrange
        List<Offer> offers = new ArrayList<>() ;
        when(offerRepository.findAllByHasAcceptedApplicants()).thenReturn(offers) ;

        //Act
        List<ApplicationDto> applicationDtos = contractService.getAcceptedApplications() ;

        //Assert
        assertNotNull(applicationDtos);
        assertSame(applicationDtos.size(), 0);

    }

    @Test
    void getContractsToEvaluateHappyDay() {
        //Arrange
        List<Contract> contracts = new ArrayList<>() ;
        for (int i = 0; i < 5; i++) {
            Contract contract1 = mock(Contract.class) ;
            when(contract1.getStudent()).thenReturn(mock(Student.class)) ;
            when(contract1.getOffer()).thenReturn(mock(Offer.class)) ;
            when(contract1.getOffer().getOwner()).thenReturn(mock(Company.class)) ;
            contract1.setEvaluationPdf(new byte[16]);
            contracts.add(contract1);
        }
        when(contractRepository.findAllByEvaluationPdfIsNull()).thenReturn(contracts) ;

        //Act
        List<ContractToEvaluateDto> contractDtos = contractService.getUnevaluatedContracts() ;

        //Assert
        assertNotNull(contractDtos);
        assertSame(contractDtos.size(), 5);

    }

    @Test
    void getContractsToEvaluateEmpty() {
        //Arrange
        List<Contract> contracts = new ArrayList<>() ;
        when(contractRepository.findAllByEvaluationPdfIsNull()).thenReturn(contracts) ;

        //Act
        List<ContractToEvaluateDto> contractDtos = contractService.getUnevaluatedContracts() ;

        //Assert
        assertNotNull(contractDtos);
        assertSame(contractDtos.size(), 0);

    }

    @Test
    void getEvaluationsHappyDay() {
        //Arrange
        List<Contract> contracts = new ArrayList<>() ;
        for (int i = 0; i < 5; i++) {
            Contract contract1 = mock(Contract.class) ;
            when(contract1.getStudent()).thenReturn(mock(Student.class)) ;
            when(contract1.getOffer()).thenReturn(mock(Offer.class)) ;
            when(contract1.getOffer().getOwner()).thenReturn(mock(Company.class)) ;
            when(contract1.getOffer().getStartDate()).thenReturn(mock(LocalDate.class)) ;
            when(contract1.getOffer().getEndDate()).thenReturn(mock(LocalDate.class)) ;
            when(contract1.getOffer().getStartDate().toString()).thenReturn("2022-10-22") ;
            when(contract1.getOffer().getEndDate().toString()).thenReturn("2022-10-22") ;
            contract1.setEvaluationPdf(new byte[16]);
            contracts.add(contract1);
        }
        when(contractRepository.findAllByEvaluationPdfIsNotNull()).thenReturn(contracts) ;

        //Act
        List<EvaluationSimpleDto> contractDtos = contractService.getEvaluations() ;

        //Assert
        assertNotNull(contractDtos);
        assertSame(contractDtos.size(), 5);

    }

    @Test
    void getEvaluationsEmpty() {
        //Arrange
        List<Contract> contracts = new ArrayList<>() ;
        when(contractRepository.findAllByEvaluationPdfIsNotNull()).thenReturn(contracts) ;

        //Act
        List<EvaluationSimpleDto> contractDtos = contractService.getEvaluations() ;

        //Assert
        assertNotNull(contractDtos);
        assertSame(contractDtos.size(), 0);

    }

    @Test
    void getEvaluationPdfHappyDay() {
        //Arrange
        Contract contract = mock(Contract.class) ;
        when(contractRepository.findById(anyLong())).thenReturn(Optional.ofNullable(contract)) ;
        when(contract.getEvaluationPdf()).thenReturn(new byte[16]) ;

        //Act
        Resource resource = contractService.getEvaluationPdf(1L) ;

        //Assert
        assertEquals(resource, new ByteArrayResource(new byte[16]));
    }

    @Test
    void getEvaluationPdfNotFound() {
        //Arrange
        when(contractRepository.findById(anyLong())).thenReturn(Optional.empty()) ;

        //Act && Assert
        assertThatThrownBy(() -> contractService.getEvaluationPdf(1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }
}
