package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.request.SignInDto;
import com.osekiller.projet.controller.payload.request.SignUpDto;
import com.osekiller.projet.controller.payload.response.JwtResponseDto;
import com.osekiller.projet.model.RefreshToken;
import com.osekiller.projet.model.Role;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.model.user.Manager;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.model.user.User;
import com.osekiller.projet.repository.RefreshTokenRepository;
import com.osekiller.projet.repository.user.*;
import com.osekiller.projet.security.JwtUtils;
import com.osekiller.projet.service.implementation.AuthServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AuthServiceTest {

    @Mock
    ManagerRepository managerRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    RefreshTokenRepository refreshTokenRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RoleRepository roleRepository ;

    @Mock
    StudentRepository studentRepository ;

    @Mock
    CompanyRepository companyRepository ;

    @Mock
    JwtUtils jwtUtils ;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void signInHappyDay() {
        // Arrange
        SignInDto mockSignIn = new SignInDto("jbiden@osk.com", "password");
        Student mockStudent = new Student("Joe Biden","jbiden@osk.com","password");

        String mockAccessToken = "accessToken";

        RefreshToken mockRefreshToken = new RefreshToken(
                UUID.randomUUID().toString(),
                mockStudent,
                Instant.now().plusMillis(RefreshToken.TOKEN_EXPIRATION)
        );

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockStudent));
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenReturn(mockRefreshToken);
        when(jwtUtils.generateToken(any(User.class))).thenReturn(mockAccessToken);

        // Act

       JwtResponseDto jwtResponseDto = authService.signIn(mockSignIn);

        // Assert
        assertThat(jwtResponseDto).isNotNull()
                .extracting("refreshToken", "accessToken").containsExactly(mockRefreshToken.getToken(), mockAccessToken);
    }

    @Test
    void signInBadEmail() {
        // Arrange

        SignInDto mockSignIn = new SignInDto("1", "password");

        //Act & Assert

        assertThatThrownBy(() -> authService.signIn(mockSignIn))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void signUpStudentHappyDay() {
        //Arrange
        SignUpDto mockSignUp = new SignUpDto("test", "test@test.com", "1", "STUDENT") ;
        Student expectedStudent = new Student("test", "test@test.com", "encrypted-pass") ;
        expectedStudent.setRole(new Role("STUDENT"));

        when(passwordEncoder.encode(anyString())).thenReturn("encrypted-pass");
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(new Role("STUDENT")));
        when(studentRepository.save(any(Student.class))).thenReturn(expectedStudent);

        //Act

        authService.signUp(mockSignUp);

        //Assert

        verify(userRepository).findByEmail(expectedStudent.getEmail());
        verify(studentRepository).save(expectedStudent) ;
    }

    @Test
    void signUpManagerHappyDay() {
        //Arrange
        SignUpDto mockSignUp = new SignUpDto("test", "test@test.com", "1", "MANAGER") ;
        Manager expectedManager = new Manager("test", "test@test.com", "encrypted-pass") ;
        expectedManager.setRole(new Role("MANAGER"));

        when(passwordEncoder.encode(anyString())).thenReturn("encrypted-pass");
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(new Role("MANAGER")));
        when(managerRepository.save(any(Manager.class))).thenReturn(expectedManager);

        //Act

        authService.signUp(mockSignUp);

        //Assert

        verify(userRepository).findByEmail(expectedManager.getEmail());
        verify(managerRepository).save(expectedManager) ;
    }

    @Test
    void signUpCompanyHappyDay() {
        //Arrange
        SignUpDto mockSignUp = new SignUpDto("test", "test@test.com", "1", "COMPANY") ;
        Company expectedCompany = new Company("test", "test@test.com", "encrypted-pass") ;
        expectedCompany.setRole(new Role("COMPANY"));

        when(passwordEncoder.encode(anyString())).thenReturn("encrypted-pass");
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(new Role("COMPANY")));
        when(companyRepository.save(any(Company.class))).thenReturn(expectedCompany);

        //Act

        authService.signUp(mockSignUp);

        //Assert

        verify(userRepository).findByEmail(expectedCompany.getEmail());
        verify(companyRepository).save(expectedCompany) ;
    }

    @Test
    void signUpEmailTaken() {
        // Arrange

        SignUpDto mockSignUp = new SignUpDto("test", "test@test.com", "1", "COMPANY");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new Company("test","test@test.com","1")));

        // Act & Assert

        assertThatThrownBy(() -> authService.signUp(mockSignUp))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void signUpInvalidRole() {
        // Arrange

        SignUpDto mockSignUp = new SignUpDto("test", "test@test.com", "1", "PROF");

        // Act & Assert

        assertThatThrownBy(() -> authService.signUp(mockSignUp))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
