package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.response.ApplicationDto;
import com.osekiller.projet.controller.payload.response.ContractDto;
import com.osekiller.projet.model.Contract;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.model.user.Manager;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.ContractRepository;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.repository.user.ManagerRepository;
import com.osekiller.projet.repository.user.SignatoryRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.service.implementation.CompanyServiceImpl;
import com.osekiller.projet.service.implementation.ContractServiceImpl;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ContractServiceTest {
    @Mock
    ContractRepository contractRepository ;

    @Mock
    OfferRepository offerRepository ;

    @Mock
    SignatoryRepository signatoryRepository;

    @Mock
    ManagerRepository managerRepository;

    @Mock
    StudentRepository studentRepository;

    @Spy
    @InjectMocks
    ContractServiceImpl contractService;

    static Student student;
    static Offer offer;
    static Manager manager;

    @BeforeAll
    static void setupAll(){
        student = new Student("student","student@email.com","123");
        student.setId(5L);
        offer = new Offer(mock(Company.class), "test",24, LocalDate.of(2022,12,12),LocalDate.of(2023,1,23));
        offer.setId(6L);
        manager = new Manager("manager","manager@email.com","123");
        manager.setId(7L);
    }

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
    void signContractNoSignature() throws IOException {
        // Arrange

        doReturn(mock(Contract.class)).when(contractService).contractSignatoryGuardClause(anyLong(),anyLong());
        doReturn(false).when(contractService).hasSignature(anyLong());

        //Act & Assert

        assertThatThrownBy(() -> contractService.signContract(1, 2))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status")
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void contractSignatoryGuardClauseHappyDay() {
        //Arrange

        when(contractRepository.findById(anyLong())).thenReturn(
                Optional.of(
                        new Contract(
                                student,
                                offer,
                                manager
                        ))
        );

        //Act

        Contract contract = contractService.contractSignatoryGuardClause(1, manager.getId());

        //Assert

        assertThat(contract).isNotNull();
        assertThat(contract.getOffer()).isEqualTo(offer);
        assertThat(contract.getManager()).isEqualTo(manager);
        assertThat(contract.getStudent()).isEqualTo(student);

    }

    @Test
    void contractSignatoryGuardClauseContractNotFound() {

        //Act & Assert
        assertThatThrownBy(() -> contractService.contractSignatoryGuardClause(1, 2))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void contractSignatoryGuardClauseSignatoryNotInContract() {

        //Arrange

        when(contractRepository.findById(anyLong())).thenReturn(
                Optional.of(
                        new Contract(
                                student,
                                offer,
                                manager
                        ))
        );

        //Act & Assert

        assertThatThrownBy(() -> contractService.contractSignatoryGuardClause(1, 2))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status")
                .isEqualTo(HttpStatus.FORBIDDEN);
    }
}
