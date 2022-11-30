package com.osekiller.projet.controller;


import com.osekiller.projet.controller.payload.response.UserDto;
import com.osekiller.projet.security.JwtUtils;
import com.osekiller.projet.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PingControllerTest {

    @MockBean
    AuthService authService;

    @MockBean
    JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc ;


    @Test
    void pingHappyDay () throws Exception {

        //Act & Assert

        mockMvc.perform(get("/ping")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    void pingTokenHappyDay () throws Exception {

        //Arrange

        UserDto dto = new UserDto("email@osk","joe",true,2L,"STUDENT");
        when(jwtUtils.getUsernameFromToken(anyString())).thenReturn("token");
        when(authService.getUserFromToken(anyString())).thenReturn(dto);


        //Act & Assert

        mockMvc.perform(get("/ping/token")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk());
    }
}
