package com.osekiller.projet.service;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
}
