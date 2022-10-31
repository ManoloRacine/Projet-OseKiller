package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.controller.payload.response.UserInfoDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.model.ERole;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.Role;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.repository.user.CompanyRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.service.implementation.OfferServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.util.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
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

    @Mock
    CompanyRepository companyRepository;

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
        mockStudent1.setApplications(new ArrayList<>());
    }

    @Test
    void getOfferHappyDay() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        Offer offer = new Offer(mock(Company.class), "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        offer.setId(1L);
        offer.setPdf(mockMultipartFile.getBytes());
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer)) ;

        assertEquals(offerService.getOffer(1L), new OfferDtoResponse(1L, "test", 1, "2002-12-14", "2002-12-16", new ByteArrayResource(mockMultipartFile.getBytes()),false,null));
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

        List<UserInfoDto> expected = offer.getApplicants().stream()
                .map(applicant ->
                        new UserInfoDto(applicant.getId(), applicant.getName(),applicant.getEmail())
                ).toList();

        //Act

        List<UserInfoDto> actual = offerService.getApplicants(1);

        //Assert

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expected);
    }

    @Test
    void addOfferHappyDay() {

        //Arrange

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        OfferDto offerDto = new OfferDto("test", 1, "2002-12-12", "2002-12-14") ;
        Company company = mock(Company.class) ;
        when(companyRepository.findById(any())).thenReturn(Optional.of(company)) ;

        offerService.addOffer(1L, offerDto, mockMultipartFile);

        verify(offerRepository).save(any()) ;
    }

    @Test
    void addOfferCompanyNonExistent() {

        //Arrange

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        OfferDto offerDto = new OfferDto("test", 1, "2002-12-12", "2002-12-14") ;

        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty()) ;

        //Act & Assert

        assertThatThrownBy(() -> offerService.addOffer(1L, offerDto, mockMultipartFile))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void modifyOfferOfferNonExistant() {
        //Arrange

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        OfferDto offerDto = new OfferDto("test", 1, "2002-12-12", "2002-12-14");

        //Act & Assert

        assertThatThrownBy(() -> offerService.modifyOffer(1, offerDto, mockMultipartFile))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void modifyOfferOfferApproved() {
        //Arrange

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes()) ;
        OfferDto offerDto = new OfferDto("test", 1, "2002-12-12", "2002-12-14");
        Offer offer = new Offer(
                company,
                "dev",
                23.50,
                LocalDate.of(2022,11,23),
                LocalDate.of(2023,1,23)
        );
        offer.setAccepted(true);
        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer));

        //Act & Assert

        assertThatThrownBy(() -> offerService.modifyOffer(1, offerDto, mockMultipartFile))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void modifyOfferHappyDay() throws IOException {
        //Arrange

        Offer offer = new Offer(
                company,
                "dev",
                23.50,
                LocalDate.of(2022,11,23),
                LocalDate.of(2023,1,23)
        );

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "test.pdf",
                "application/pdf",
                "test".getBytes()
        );

        OfferDto offerDto = new OfferDto("test", 1, "2002-12-12", "2002-12-14");

        Offer offerExpected = new Offer(
                company,
                offerDto.position(),
                offerDto.salary(),
                LocalDate.parse(offerDto.startDate()),
                LocalDate.parse(offerDto.endDate())
        );

        offerExpected.setPdf(mockMultipartFile.getBytes());
        offerExpected.setPdfName(StringUtils.cleanPath(mockMultipartFile.getOriginalFilename()));

        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer));

        //Act

        offerService.modifyOffer(1, offerDto, mockMultipartFile);

        //Assert

        assertThat(offer).isEqualTo(offerExpected);
        verify(offerRepository).save(offer);
    }

    @Test
    void getValidOffersBySessionHappyDay() {
        Company company = mock(Company.class);
        Offer offer1 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        Offer offer2 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        Offer offer3 = new Offer(company, "test", 1., LocalDate.of(2003, 12, 14), LocalDate.of(2002, 12, 16)) ;
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
        when(offerRepository.findAllByAcceptedIsTrue()).thenReturn(mockList) ;

        List<GeneralOfferDto> list = offerService.getAllValidOffersBySession(2003) ;

        assertNotNull(list);
        assertSame(2, list.size());
        assertEquals(list, mockDtoList);
    }

    @Test
    void getValidOffersBySessionEmpty() {
        Company company = mock(Company.class);
        Offer offer1 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        Offer offer2 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        Offer offer3 = new Offer(company, "test", 1., LocalDate.of(2003, 12, 14), LocalDate.of(2002, 12, 16)) ;
        offer1.setId(1L);
        offer2.setId(2L);
        offer3.setId(3L);
        GeneralOfferDto offerDto1 = new GeneralOfferDto(offer1.getId(), 1L, "google", offer1.getPosition(), offer1.getSalary(), offer1.getStartDate().toString(), offer1.getEndDate().toString()) ;
        GeneralOfferDto offerDto2 = new GeneralOfferDto(offer2.getId(),1L, "google", offer2.getPosition(), offer2.getSalary(), offer2.getStartDate().toString(), offer2.getEndDate().toString()) ;
        GeneralOfferDto offerDto3 = new GeneralOfferDto(offer3.getId(),1L, "google", offer3.getPosition(), offer3.getSalary(), offer3.getStartDate().toString(), offer3.getEndDate().toString()) ;
        List<Offer> mockList = new ArrayList<>() ;
        mockList.add(offer1) ;
        mockList.add(offer2) ;
        mockList.add(offer3) ;
        when(offerRepository.findAllByAcceptedIsTrue()).thenReturn(mockList) ;

        List<GeneralOfferDto> list = offerService.getAllValidOffersBySession(1) ;

        assertNotNull(list);
        assertSame(list.size(), 0);
    }

    @Test
    void getInvalidOffersBySessionHappyDay() {
        Company company = mock(Company.class);
        Offer offer1 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        Offer offer2 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        Offer offer3 = new Offer(company, "test", 1., LocalDate.of(2003, 12, 14), LocalDate.of(2002, 12, 16)) ;
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
        when(offerRepository.findAllByAcceptedIsFalseAndFeedbackIsNull()).thenReturn(mockList) ;

        List<GeneralOfferDto> list = offerService.getAllInvalidOffersBySession(2003) ;

        assertNotNull(list);
        assertSame(2, list.size());
        assertEquals(list, mockDtoList);
    }

    @Test
    void getInvalidOffersBySessionEmpty() {
        Company company = mock(Company.class);
        Offer offer1 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        Offer offer2 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
        Offer offer3 = new Offer(company, "test", 1., LocalDate.of(2003, 12, 14), LocalDate.of(2002, 12, 16)) ;
        offer1.setId(1L);
        offer2.setId(2L);
        offer3.setId(3L);
        GeneralOfferDto offerDto1 = new GeneralOfferDto(offer1.getId(), 1L, "google", offer1.getPosition(), offer1.getSalary(), offer1.getStartDate().toString(), offer1.getEndDate().toString()) ;
        GeneralOfferDto offerDto2 = new GeneralOfferDto(offer2.getId(),1L, "google", offer2.getPosition(), offer2.getSalary(), offer2.getStartDate().toString(), offer2.getEndDate().toString()) ;
        GeneralOfferDto offerDto3 = new GeneralOfferDto(offer3.getId(),1L, "google", offer3.getPosition(), offer3.getSalary(), offer3.getStartDate().toString(), offer3.getEndDate().toString()) ;
        List<Offer> mockList = new ArrayList<>() ;
        mockList.add(offer1) ;
        mockList.add(offer2) ;
        mockList.add(offer3) ;
        when(offerRepository.findAllByAcceptedIsFalseAndFeedbackIsNull()).thenReturn(mockList) ;

        List<GeneralOfferDto> list = offerService.getAllInvalidOffersBySession(1) ;

        assertNotNull(list);
        assertSame(list.size(), 0);
    }


    @Test
    void acceptApplicantOfferNotFound(){

        //Act & Assert
        assertThatThrownBy(() -> offerService.acceptApplicantForOffer(1,2))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void acceptApplicantStudentNotFound(){
        //Arrange
        when(offerRepository.findByIdAndFetchApplicants(anyLong())).thenReturn(Optional.of(mock(Offer.class)));

        //Act & Assert
        assertThatThrownBy(() -> offerService.acceptApplicantForOffer(1,2))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void acceptApplicantStudentDidNotApply(){
        //Arrange
        when(offerRepository.findByIdAndFetchApplicants(anyLong())).thenReturn(Optional.of(mock(Offer.class)));
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(mock(Student.class)));

        //Act & Assert
        assertThatThrownBy(() -> offerService.acceptApplicantForOffer(1,2))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void acceptApplicantStudentAlreadyAccepted(){
        //Arrange
        offer.getApplicants().add(mockStudent1);
        offer.getAcceptedApplicants().add(mockStudent1);

        when(offerRepository.findByIdAndFetchApplicants(anyLong())).thenReturn(Optional.of(offer));
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(mockStudent1));

        //Act & Assert
        assertThatThrownBy(() -> offerService.acceptApplicantForOffer(1,2))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void acceptApplicantHappyDay(){
        //Arrange
        offer.getApplicants().add(mockStudent1);


        when(offerRepository.findByIdAndFetchApplicants(anyLong())).thenReturn(Optional.of(offer));
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(mockStudent1));

        //Act
        offerService.acceptApplicantForOffer(1,2);

        //Assert
        assertThat(offer.getAcceptedApplicants()).contains(mockStudent1);
        assertThat(mockStudent1.getAcceptedApplications()).contains(offer);

    }
}
