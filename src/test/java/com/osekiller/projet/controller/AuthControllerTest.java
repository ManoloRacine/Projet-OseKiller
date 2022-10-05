package com.osekiller.projet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osekiller.projet.controller.payload.request.JwtRequestDto;
import com.osekiller.projet.controller.payload.request.SignInDto;
import com.osekiller.projet.controller.payload.request.SignUpDto;
import com.osekiller.projet.controller.payload.response.JwtResponseDto;
import com.osekiller.projet.model.ERole;
import com.osekiller.projet.model.RefreshToken;
import com.osekiller.projet.model.Role;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.service.AuthService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AuthService authService;

    @MockBean
    AuthenticationManager authenticationManager;

    static Student mockStudent;
    static RefreshToken mockRefreshToken;
    static String mockAccessToken;

    @BeforeAll
    static void setup(){
        mockStudent = new Student("Joe Biden","jbiden@osk.com","encrypted-pass");
        mockStudent.setRole(new Role(ERole.STUDENT.name()));

        mockRefreshToken = new RefreshToken(
                UUID.randomUUID().toString(),
                mockStudent,
                Instant.now().plusMillis(RefreshToken.TOKEN_EXPIRATION)
        );

        mockAccessToken = "access-token";
    }

    @Test
    void signInHappyDay() throws Exception {

        //Arrange
        SignInDto signInDto = new SignInDto("jbiden@osk.com", "password");
        JwtResponseDto expectedJwtResponseDto = new JwtResponseDto(
                mockAccessToken,
                mockRefreshToken.getToken(),
                "Bearer"
        );

        when(authService.signIn(any(SignInDto.class))).thenReturn(expectedJwtResponseDto);

        //Act & Assert

        MvcResult mvcResult = mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(signInDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        assertThat(content)
                .isNotNull()
                .isNotBlank()
                .isEqualTo(asJsonString(expectedJwtResponseDto));
    }

    @Test
    void signInBadCredentials() throws Exception {
        //Arrange

        SignInDto signInDto = new SignInDto("jbiden@osk.com", "password");
        when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(BadCredentialsException.class);

        //Act & Assert

        mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(signInDto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void signUpHappyDay() throws Exception {
        //Arrange

        SignUpDto mockSignUp = new SignUpDto("test", "test@test.com", "1", "STUDENT");

        //Act & Assert

        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mockSignUp)))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser
    void signOutHappyDay() throws Exception {

        //Act & Assert

        mockMvc.perform(post("/sign-out")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new JwtRequestDto("refresh-token"))))
                .andExpect(status().isNoContent());

    }

    @Test
    void signOutUnauthenticated() throws Exception {

        //Act & Assert

        mockMvc.perform(post("/sign-out")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new JwtRequestDto("refresh-token"))))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void refreshHappyDay() throws Exception {
        //Arrange

        JwtResponseDto expectedJwtResponseDto = new JwtResponseDto(
                mockAccessToken,
                mockRefreshToken.getToken(),
                "Bearer"
        );

        when(authService.refresh(anyString())).thenReturn(expectedJwtResponseDto);

        //Act & Assert

        MvcResult mvcResult = mockMvc.perform(post("/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new JwtRequestDto("refresh-token"))))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        assertThat(content)
                .isNotNull()
                .isNotBlank()
                .isEqualTo(asJsonString(expectedJwtResponseDto));
    }

    @Test
    void refreshUnauthenticated() throws Exception {

        //Act & Assert

        mockMvc.perform(post("/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new JwtRequestDto("refresh-token"))))
                .andExpect(status().isForbidden());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
