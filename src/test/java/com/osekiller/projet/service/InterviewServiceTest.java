package com.osekiller.projet.service;

import com.osekiller.projet.model.Interview;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.InterviewRepository;
import com.osekiller.projet.repository.user.CompanyRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.service.implementation.InterviewServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class InterviewServiceTest {
    @Mock
    StudentRepository studentRepository;
    @Mock
    CompanyRepository companyRepository;
    @Mock
    InterviewRepository interviewRepository;
    @InjectMocks
    InterviewServiceImpl interviewService;

    @Test
    void inviteApplicantToInterviewHappyDay(){

        //Arrange

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(mock(Student.class)));
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(mock(Company.class)));

        //Act

        interviewService.inviteApplicantToInterview(1,1,List.of(LocalDate.now().plusDays(1),LocalDate.now().plusDays(2),LocalDate.now().plusDays(3)));

        //Assert

        verify(interviewRepository).save(any(Interview.class));
    }
    @Test
    void inviteApplicantToInterviewApplicationNotFound(){
        // Act & Assert

        assertThatThrownBy(() -> interviewService.inviteApplicantToInterview(1,1, List.of(mock(LocalDate.class),mock(LocalDate.class),mock(LocalDate.class))))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Test
    void inviteApplicantToInterviewCompanyNotFound(){
        //Arrange

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(mock(Student.class)));

        // Act & Assert

        assertThatThrownBy(() -> interviewService.inviteApplicantToInterview(1,1, List.of(mock(LocalDate.class),mock(LocalDate.class),mock(LocalDate.class))))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void inviteApplicantToInterviewNotEnoughDates(){
        //Arrange

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(mock(Student.class)));
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(mock(Company.class)));

        // Act & Assert

        assertThatThrownBy(() -> interviewService.inviteApplicantToInterview(1,1, List.of(mock(LocalDate.class),mock(LocalDate.class))))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void inviteApplicantToInterviewTooManyDates(){
        //Arrange

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(mock(Student.class)));
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(mock(Company.class)));

        // Act & Assert

        assertThatThrownBy(() -> interviewService.inviteApplicantToInterview(1,1, List.of(mock(LocalDate.class),mock(LocalDate.class),mock(LocalDate.class),mock(LocalDate.class))))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void inviteApplicantToInterviewDatesBeforeNow(){
        //Arrange

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(mock(Student.class)));
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(mock(Company.class)));

        // Act & Assert

        assertThatThrownBy(() -> interviewService.inviteApplicantToInterview(1,1, List.of(LocalDate.now().minusDays(1),LocalDate.now().minusDays(2),LocalDate.now().minusDays(3))))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.CONFLICT);
    }
}
