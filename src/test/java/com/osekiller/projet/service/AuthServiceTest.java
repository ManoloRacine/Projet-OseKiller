package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.request.SignInDto;
import com.osekiller.projet.controller.payload.request.SignUpDto;
import com.osekiller.projet.controller.payload.response.JwtResponseDto;
import com.osekiller.projet.controller.payload.response.UserDto;
import com.osekiller.projet.model.ERole;
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    static Student mockStudent;
    static Manager mockManager;
    static Company mockCompany;
    static RefreshToken mockRefreshToken;
    static String mockAccessToken;
    @BeforeAll
    static void setup(){
        mockStudent = new Student("Joe Biden","jbiden@osk.com","encrypted-pass");
        mockStudent.setRole(new Role(ERole.STUDENT.name()));

        mockManager = new Manager("Obama Barrack", "obarrack@osk.com", "encrypted-pass") ;
        mockManager.setRole(new Role(ERole.MANAGER.name()));

        mockCompany = new Company("Tesla", "tesla.internship@osk.com", "encrypted-pass") ;
        mockCompany.setRole(new Role(ERole.COMPANY.name()));

        mockRefreshToken = new RefreshToken(
                UUID.randomUUID().toString(),
                mockStudent,
                Instant.now().plusMillis(RefreshToken.TOKEN_EXPIRATION)
        );

        mockAccessToken = "access-token";
    }
    @Test
    void signInHappyDay() {
        // Arrange
        SignInDto mockSignIn = new SignInDto("jbiden@osk.com", "password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockStudent));
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenReturn(mockRefreshToken);
        when(jwtUtils.generateToken(any(User.class))).thenReturn(mockAccessToken);

        // Act

       JwtResponseDto jwtResponseDto = authService.signIn(mockSignIn);

        // Assert
        assertThat(jwtResponseDto).isNotNull()
                .extracting("refreshToken", "accessToken")
                .containsExactly(mockRefreshToken.getToken(), mockAccessToken);
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
        SignUpDto mockSignUp = new SignUpDto("test", "test@test.com", "1", "STUDENT");

        when(passwordEncoder.encode(anyString())).thenReturn("encrypted-pass");
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(new Role("STUDENT")));
        when(studentRepository.save(any(Student.class))).thenReturn(mockStudent);

        //Act

        authService.signUp(mockSignUp);

        //Assert

        verify(userRepository).findByEmail(mockSignUp.email());
        verify(studentRepository).save(any(Student.class)) ;
    }
    @Test
    void signUpManagerHappyDay() {
        //Arrange
        SignUpDto mockSignUp = new SignUpDto("test", "test@test.com", "1", "MANAGER") ;

        when(passwordEncoder.encode(anyString())).thenReturn("encrypted-pass");
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(new Role("MANAGER")));
        when(managerRepository.save(any(Manager.class))).thenReturn(mockManager);

        //Act

        authService.signUp(mockSignUp);

        //Assert

        verify(userRepository).findByEmail(mockSignUp.email());
        verify(managerRepository).save(any(Manager.class));
    }
    @Test
    void signUpCompanyHappyDay() {
        //Arrange
        SignUpDto mockSignUp = new SignUpDto("test", "test@test.com", "1", "COMPANY") ;

        when(passwordEncoder.encode(anyString())).thenReturn("encrypted-pass");
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(new Role("COMPANY")));
        when(companyRepository.save(any(Company.class))).thenReturn(mockCompany);

        //Act

        authService.signUp(mockSignUp);

        //Assert

        verify(userRepository).findByEmail(mockSignUp.email());
        verify(companyRepository).save(any(Company.class)) ;
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
    @Test
    void signOutHappyDay() {
        //Arrange

        when(refreshTokenRepository.findByToken(anyString())).thenReturn(Optional.of(mockRefreshToken));

        //Act

        authService.signOut("refresh-token");

        //Assert

        verify(refreshTokenRepository).delete(mockRefreshToken);
    }

    @Test
    void signOutInvalidToken() {
        //Act & Assert

        assertThatThrownBy(() -> authService.signOut("refresh-token"))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void refreshHappyDay(){
        //Arrange

        when(refreshTokenRepository.findByToken(anyString())).thenReturn(Optional.of(mockRefreshToken));
        when(jwtUtils.generateToken(any(User.class))).thenReturn(mockAccessToken);

        //Act

        JwtResponseDto jwtResponseDto = authService.refresh("refresh-token");

        //Assert

        assertThat(jwtResponseDto).isNotNull()
                .extracting("refreshToken", "accessToken")
                .containsExactly(mockRefreshToken.getToken(), mockAccessToken);
    }
    @Test
    void refreshInvalidToken(){
        //Act & Assert

        assertThatThrownBy(() -> authService.refresh("refresh-token"))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.UNAUTHORIZED);
    }
    @Test
    void refreshExpiredToken(){
        RefreshToken mockExpiredResfreshToken = new RefreshToken(
                UUID.randomUUID().toString(),
                mockStudent,
                Instant.now().minusMillis(RefreshToken.TOKEN_EXPIRATION)
        );

        when(refreshTokenRepository.findByToken(anyString())).thenReturn(Optional.of(mockExpiredResfreshToken));

        //Act & Assert

        assertThatThrownBy(() -> authService.refresh("refresh-token"))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status", "reason")
                .containsExactly(HttpStatus.UNAUTHORIZED,"token-expired");
    }

    @Test
    void getUserFromTokenHappyDay(){
        //Arrange

        when(jwtUtils.getUsernameFromToken(anyString())).thenReturn(mockStudent.getEmail());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(mockStudent));

        //Act

        UserDto userDto = authService.getUserFromToken(mockAccessToken);

        //Assert

        assertThat(userDto).isNotNull()
                .extracting("id", "email", "name", "enabled", "role")
                .containsExactly(
                        mockStudent.getId(),
                        mockStudent.getEmail(),
                        mockStudent.getName(),
                        mockStudent.isEnabled(),
                        mockStudent.getRole().getName()
                );
    }

    @Test
    void getUserFromTokenInalidToken(){
        //Act & Assert

        assertThatThrownBy(() -> authService.getUserFromToken(mockAccessToken))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
