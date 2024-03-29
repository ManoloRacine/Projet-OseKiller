package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.response.ApplicationDto;
import com.osekiller.projet.controller.payload.response.ContractDto;
import com.osekiller.projet.controller.payload.response.ContractToEvaluateDto;
import com.osekiller.projet.controller.payload.response.EvaluationSimpleDto;
import com.osekiller.projet.model.Contract;
import com.osekiller.projet.model.Cv;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.Role;
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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HexFormat;
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
    static List<Contract> contracts;

    @BeforeAll
    static void setupAll(){
        student = new Student("student","student@email.com","123");
        student.setId(5L);
        offer = new Offer(mock(Company.class), "test",24, LocalDate.of(2022,12,12),LocalDate.of(2023,1,23));
        offer.setId(6L);
        manager = new Manager("manager","manager@email.com","123");
        manager.setId(7L);
        contracts = List.of(
                new Contract(
                        student,
                        new Offer(
                                new Company(
                                        "Bro",
                                        "Bro@osk.com",
                                        "123"
                                ),
                                "Dev Java",
                                20,
                                LocalDate.of(2022, 12, 12),
                                LocalDate.of(2023, 1, 12)
                        ),
                        manager
                ),
                new Contract(
                        student,
                        new Offer(
                                new Company(
                                        "Bro",
                                        "Bro@osk.com",
                                        "123"
                                ),
                                "Dev JavaScript",
                                20,
                                LocalDate.of(2022, 12, 12),
                                LocalDate.of(2023, 1, 12)
                        ),
                        manager
                ),
                new Contract(
                        student,
                        new Offer(
                                new Company(
                                        "Bro",
                                        "Bro@osk.com",
                                        "123"
                                ),
                                "IT",
                                20,
                                LocalDate.of(2022, 12, 12),
                                LocalDate.of(2023, 1, 12)
                        ),
                        manager
                ));
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

    @Test
    void getReportPdfHappy() {
        //Arrange
        Contract contract = mock(Contract.class) ;
        when(contractRepository.findById(anyLong())).thenReturn(Optional.ofNullable(contract)) ;
        when(contract.getReport()).thenReturn(new byte[16]) ;

        //Act
        Resource resource = contractService.getReport(1L) ;

        //Assert
        assertEquals(resource, new ByteArrayResource(new byte[16]));
    }

    @Test
    void getReportNotFound() {
        //Arrange
        when(contractRepository.findById(anyLong())).thenReturn(Optional.empty()) ;

        //Act && Assert
        assertThatThrownBy(() -> contractService.getReport(1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getReportNull() {
        //Arrange
        Contract contract = mock(Contract.class) ;
        when(contractRepository.findById(anyLong())).thenReturn(Optional.ofNullable(contract)) ;
        when(contract.getReport()).thenReturn(null) ;

        //Act && Assert
        assertThatThrownBy(() -> contractService.getReport(1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void saveReportHappyDay() throws IOException {
        // Arrange
        Contract mockContract = mock(Contract.class) ;
        Student mockStudent = mock(Student.class) ;
        when(mockContract.getStudent()).thenReturn(mockStudent) ;
        when(mockStudent.getId()).thenReturn(2L) ;
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "test".getBytes()
        ) ;

        when(contractRepository.findById(any())).thenReturn(Optional.of(mockContract));

        // Act
        contractService.saveReport(mockFile, 1L, 2L);

        // Assert
        verify(contractRepository).save(any(Contract.class)) ;
        verify(mockContract).setReport(mockFile.getBytes()) ;
    }

    @Test
    void saveReportContractNotFound() {
        //Arrange
        when(contractRepository.findById(1L)).thenReturn(Optional.empty()) ;
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "test".getBytes()
        ) ;

        //Act & Assert
        assertThatThrownBy(() -> contractService.saveReport(mockFile, 1L, 2L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);


    }

    @Test
    void saveReportNotStudent() {
        //Arrange
        Contract mockContract = mock(Contract.class) ;
        when(contractRepository.findById(1L)).thenReturn(Optional.of(mockContract)) ;
        Student mockStudent = mock(Student.class) ;
        when(mockContract.getStudent()).thenReturn(mockStudent) ;
        when(mockStudent.getId()).thenReturn(5L) ;
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "test".getBytes()
        ) ;

        //Arrange
        assertThatThrownBy(() -> contractService.saveReport(mockFile, 1L, 2L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.UNAUTHORIZED);


    }

    @Test
    void getContractHappyDay() throws IOException {
        //Arrange
        Contract contract = mock(Contract.class);
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "test".getBytes()
        ) ;


        when(contractRepository.findById(anyLong())).thenReturn(Optional.of(contract));
        when(contract.getPdf()).thenReturn(mockFile.getBytes());

        //Act

        Resource pdfContract = contractService.getContract(1L);

        //Assert

        assertThat(pdfContract.getClass()).isEqualTo(ByteArrayResource.class) ;
    }

    @Test
    void hasSignatureHappyDay() throws IOException {
        //Arrange

        when(signatoryRepository.findByIdAndSignatureIsNotNull(anyLong())).thenReturn(Optional.of(mock(Student.class)));

        //Act

        boolean result = contractService.hasSignature(2L);

        //Assert

        assertThat(result).isTrue();
    }

    @Test
    void getContractsByManagerIdHappyDay() {
        //Arrange

        when(contractRepository.findAllByManager_Id(anyLong())).thenReturn(contracts);

        //Act


        List<ContractDto> contractDtos = contractService.getContractsByManagerId(2L);

        //Assert

        assertThat(contractDtos).isNotNull();
        assertThat(contractDtos.size()).isEqualTo(contracts.size());
    }

    @Test
    void getContractsByStudentIdHappyDay() {
        //Arrange

        when(contractRepository.findAllByStudent_Id(anyLong())).thenReturn(contracts);

        //Act


        List<ContractDto> contractDtos = contractService.getContractsByStudentId(2L);

        //Assert

        assertThat(contractDtos).isNotNull();
        assertThat(contractDtos.size()).isEqualTo(contracts.size());
    }

    @Test
    void getContractsByCompanyIdHappyDay() {
        //Arrange

        when(contractRepository.findAllByOffer_Owner_Id(anyLong())).thenReturn(contracts);

        //Act


        List<ContractDto> contractDtos = contractService.getContractsByCompanyId(2L);

        //Assert

        assertThat(contractDtos).isNotNull();
        assertThat(contractDtos.size()).isEqualTo(contracts.size());
    }


    @Test
    void getContractWithInternEvaluationsHappyDay() {
        //Arrange

        when(contractRepository.findAllByStudentEvaluationPdfIsNotNull()).thenReturn(contracts);

        //Act

        List<ContractDto> contractDtos = contractService.getContractWithInternEvaluations();

        //Assert

        assertThat(contractDtos).isNotNull();
        assertThat(contractDtos.size()).isEqualTo(contracts.size());
    }
}
