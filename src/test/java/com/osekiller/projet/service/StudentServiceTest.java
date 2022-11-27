package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.controller.payload.response.InterviewDto;
import com.osekiller.projet.controller.payload.response.StudentWithCvStateDto;
import com.osekiller.projet.model.Cv;
import com.osekiller.projet.model.Interview;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.Role;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.model.user.Manager;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.CvRepository;
import com.osekiller.projet.repository.InterviewRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.service.implementation.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @Mock
    CurrentDateFactory currentDateFactory;

    @Mock
    CvRepository cvRepository;

    @Mock
    InterviewRepository interviewRepository;

    @Mock
    NotificationsService notificationsService;

    @InjectMocks
    private StudentServiceImpl studentService ;

    @Test
    void saveCVHappyDay() {
        // Arrange
        Student mockStudent = new Student("Joe Biden","jbiden@osk.com","password");
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "test".getBytes()
        ) ;
        Cv mockCv = new Cv(mockStudent, false);
        when(studentRepository.findById(any())).thenReturn(Optional.of(mockStudent));
        when(cvRepository.findCVByOwner(any())).thenReturn(mockCv);

        // Act
        studentService.saveCV(mockFile, 1L);

        // Assert
        verify(studentRepository).save(any(Student.class)) ;
        assertThat(cvRepository.findCVByOwner(mockStudent)).isNotNull();
    }

    @Test
    void saveCVNotStudent() {
        //Arrange
        Manager mockManager = new Manager("Joe Biden","jbiden@osk.com","password");
        mockManager.setRole(new Role("MANAGER"));
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes()) ;

        //Act & Assert
        assertThatThrownBy(() -> studentService.saveCV(mockFile, 1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.UNAUTHORIZED);


    }

    @Test
    void getCVHappyDay() throws IOException {
        //Arrange
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "test".getBytes()
        ) ;
        Cv cv = mock(Cv.class) ;
        Student student = mock(Student.class) ;
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student)) ;
        when(student.getCv()).thenReturn(cv) ;
        when(cvRepository.findById(anyLong())).thenReturn(Optional.of(cv)) ;
        when(cv.getId()).thenReturn(1L) ;
        when(cv.getPdf()).thenReturn(mockFile.getBytes()) ;

        //Act
        Resource resourceReturn = studentService.getCV(1L) ;

        assertThat(resourceReturn.getClass()).isEqualTo(ByteArrayResource.class) ;
    }

    @Test
    void getCVDoesntExist() {
        //Act & Assert
        assertThatThrownBy(() -> studentService.getCV(1L) )
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);

    }


    @Test
    void getStudentsHappyDay(){

        //Arrange

        Student student1 = new Student("Joe", "jbiden@osk.com", "password");
        Student student2 = new Student("Obama", "obarrack@osk.com", "password");
        Student student3 = new Student("Trump", "tdonald@osk.com", "password");

        List<Student> students = List.of(student1, student2, student3);

        List<StudentWithCvStateDto> expected = students.stream().map(
                StudentWithCvStateDto::from
        ).toList();

        when(studentRepository.findAll()).thenReturn(students);

        //Act

        List<StudentWithCvStateDto> actual = studentService.getStudents();

        //Assert

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expected);
    }

    @Test
    void getStudentNotFound(){
        //Act & Assert

        assertThatThrownBy(() -> studentService.getStudent(1))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getStudentHappyDay() {

        //Arrange

        Student student = new Student("Joe", "jbiden@osk.com", "password");

        StudentWithCvStateDto expected = StudentWithCvStateDto.from(student);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        //Act

        StudentWithCvStateDto actual = studentService.getStudent(1);

        //Assert

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expected);
    }

    @Test
    void validateCvNotfound(){

        //Act & Assert

        assertThatThrownBy(() -> studentService.validateCV(1, "This is one the the resumes of all time"))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void validateCvHappyDay(){

        // Arrange

        Student student = new Student("Joe", "jbiden@osk.com", "password");

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        // Act

        studentService.validateCV(1, "This is one the the resumes of all time");

        // Assert

        assertThat(student.isCvRejected())
                .isEqualTo(false);
        assertThat(student.getCv().isValidated())
                .isEqualTo(true);
        assertThat(student.getCv().getFeedback())
                .isNotBlank()
                .isEqualTo("This is one the the resumes of all time");
    }

    @Test
    void invalidateCvNotfound(){

        //Act & Assert

        assertThatThrownBy(() -> studentService.invalidateCV(1, "This is one the the resumes of all time"))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void invalidateCvHappyDay(){

        // Arrange

        Student student = new Student("Joe", "jbiden@osk.com", "password");

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        // Act

        studentService.invalidateCV(1, "This is one the the resumes of all time");

        // Assert

        assertThat(student.isCvRejected())
                .isEqualTo(true);
        assertThat(student.getCv().isValidated())
                .isEqualTo(false);
        assertThat(student.getCv().getFeedback())
                .isNotBlank()
                .isEqualTo("This is one the the resumes of all time");
    }

    @Test
    void getApplicationsStudentNotFound() {
        //Act & Assert

        assertThatThrownBy(() -> studentService.getApplications(1))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getApplicationsHappyDay(){

        // Arrange

        Student student = new Student("Joe", "jbiden@osk.com", "password");

        Company company = mock(Company.class);
        Offer offer1 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16));
        Offer offer2 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16));
        Offer offer3 = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16));
        student.setApplications(List.of(offer1, offer2, offer3));

        List<GeneralOfferDto> expected = student.getApplications().stream().map(GeneralOfferDto::from).toList();

        when(studentRepository.findByIdAndFetchApplications(anyLong())).thenReturn(Optional.of(student));

        // Act

        List<GeneralOfferDto> actual = studentService.getApplications(1);

        // Assert

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expected);
    }


    @Test
    void getCurrentSessionHappyDay() {
        //Arrange
        when(currentDateFactory.getCurrentDate()).thenReturn(LocalDate.of(2023, 1, 1)) ;

        //Act
        int currentSession = studentService.getCurrentSession() ;

        //Assert
        assertThat(currentSession).isEqualTo(2023) ;
    }

    @Test
    void getCurrentSessionAfterDate() {
        //Arrange
        when(currentDateFactory.getCurrentDate()).thenReturn(LocalDate.of(2023, 6, 1)) ;

        //Act
        int currentSession = studentService.getCurrentSession() ;

        //Assert
        assertThat(currentSession).isEqualTo(2024) ;
    }

    @Test
    void updateSessionHappyDay() {
        //Arrange
        Student student = mock(Student.class) ;
        Cv cv = mock(Cv.class) ;
        when(student.getCv()).thenReturn(cv) ;
        when(currentDateFactory.getCurrentDate()).thenReturn(LocalDate.of(2023, 1, 1)) ;
        when(studentRepository.findById(any())).thenReturn(Optional.of(student)) ;

        //Act
        studentService.updateSession(1) ;

        //Assert
        verify(student).setSessionYear(2023);
        verify(studentRepository).save(student) ;
    }

    @Test
    void updateSessionAfterDate() {
        //Arrange
        Student student = mock(Student.class) ;
        Cv cv = mock(Cv.class) ;
        when(student.getCv()).thenReturn(cv) ;
        when(currentDateFactory.getCurrentDate()).thenReturn(LocalDate.of(2023, 6, 1)) ;
        when(studentRepository.findById(any())).thenReturn(Optional.of(student)) ;

        //Act
        studentService.updateSession(1) ;

        //Assert
        verify(student).setSessionYear(2024);
        verify(studentRepository).save(student) ;
    }

    @Test
    void getInterviewsStudentNotFound(){
        //Act & Assert

        assertThatThrownBy(() -> studentService.getInterviews(1))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getInterviewsHappyDay(){
        //Arrange

        Student student = new Student("Joe", "jbiden@osk.com", "password");
        student.setId(1L);
        Company company = mock(Company.class);
        Offer offer = new Offer(company, "test", 1., LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16));
        offer.setId(2L);

        Interview interview1 = new Interview(offer, student, List.of(LocalDate.now().plusDays(4),LocalDate.now().plusDays(7),LocalDate.now().plusDays(15)));
        interview1.setId(3L);
        Interview interview2 = new Interview(offer, student, List.of(LocalDate.now().plusDays(4),LocalDate.now().plusDays(7),LocalDate.now().plusDays(15)));
        interview2.setId(4L);
        Interview interview3 = new Interview(offer, student, List.of(LocalDate.now().plusDays(4),LocalDate.now().plusDays(7),LocalDate.now().plusDays(15)));
        interview3.setId(5L);

        List<InterviewDto> expected = List.of(interview1, interview2, interview3).stream().map(InterviewDto::from).toList();

        when(studentRepository.existsById(anyLong())).thenReturn(true);
        when(interviewRepository.findAllByInterviewee_Id(anyLong())).thenReturn(List.of(interview1, interview2, interview3));

        //Act

        List<InterviewDto> actual = studentService.getInterviews(1L);

        //Assert

        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void confirmInterviewHappyDay(){
        //Arrange

        Student student = mock(Student.class);
        Offer offer = mock(Offer.class);

        Interview interview = new Interview(
                offer,
                student,
                List.of(
                        LocalDate.of(2022,10,24),
                        LocalDate.of(2022,10,26),
                        LocalDate.of(2022,10,30)
                )
        );

        Interview interviewAfter = new Interview(
                offer,
                student,
                List.of(
                        LocalDate.of(2022,10,24),
                        LocalDate.of(2022,10,26),
                        LocalDate.of(2022,10,30)
                )
        );

        interviewAfter.setChosenInterviewDate( LocalDate.of(2022,10,24));

        when(interviewRepository.findById(anyLong())).thenReturn(Optional.of(interview));
        when(offer.getOwner()).thenReturn(mock(Company.class)) ;

        //Act

        studentService.confirmInterview(1L,"2022-10-24");

        //Assert

        assertThat(interview).isEqualTo(interviewAfter);
        verify(interviewRepository).save(interview);
    }

    @Test
    void confirmInterviewDateNotProposed(){
        //Arrange

        Student student = mock(Student.class);
        Offer offer = mock(Offer.class);

        Interview interview = new Interview(
                offer,
                student,
                List.of(
                        LocalDate.of(2022,10,25),
                        LocalDate.of(2022,10,26),
                        LocalDate.of(2022,10,30)
                )
        );

        when(interviewRepository.findById(anyLong())).thenReturn(Optional.of(interview));

        //Act & Assert

        assertThatThrownBy(() -> studentService.confirmInterview(1L,"2022-10-24"))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.CONFLICT);
    }


}
