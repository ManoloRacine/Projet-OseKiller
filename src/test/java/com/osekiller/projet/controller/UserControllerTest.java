package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.request.UserValidationDto;
import com.osekiller.projet.controller.payload.response.UserDto;
import com.osekiller.projet.model.Role;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.model.user.Manager;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.model.user.User;
import com.osekiller.projet.repository.user.UserRepository;
import com.osekiller.projet.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
    private UserServiceImpl userService ;

    @Mock
    UserRepository userRepository ;

    @Autowired
    private MockMvc mockMvc ;

    @Test
    @WithMockUser
    void getUsersHappyDay() throws Exception {
        Student mockUser1 = new Student("test1", "test@student.com", "1") ;
        mockUser1.setRole(new Role("STUDENT"));
        UserDto mockDto1 = new UserDto(mockUser1.getEmail(), mockUser1.getName(), mockUser1.isEnabled(), mockUser1.getId(), mockUser1.getRole().getName()) ;
        Manager mockUser2 = new Manager("test2", "test@manager.com", "1") ;
        mockUser2.setRole(new Role("MANAGER"));
        UserDto mockDto2 = new UserDto(mockUser2.getEmail(), mockUser2.getName(), mockUser2.isEnabled(), mockUser2.getId(), mockUser2.getRole().getName()) ;
        Company mockUser3 = new Company("test3", "test@company.com", "1") ;
        mockUser3.setRole(new Role("COMPANY"));
        UserDto mockDto3 = new UserDto(mockUser3.getEmail(), mockUser3.getName(), mockUser3.isEnabled(), mockUser3.getId(), mockUser3.getRole().getName()) ;
        List<UserDto> mockDtoList = new ArrayList<UserDto>(Arrays.asList(mockDto1, mockDto2, mockDto3)) ;
        doReturn(mockDtoList).when(userService).getUsers() ;

        mockMvc.perform(get("/users"))
    }

    @Test
    @WithMockUser
    void validateHappyday() throws Exception {
        Student mockUser1 = new Student("test1", "test@student.com", "1") ;
        doReturn(Optional.of(mockUser1)).when(userRepository).findById(1L) ;

        mockMvc.perform(post("/user/{id}/validate", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new UserValidationDto("test@test.com", true))))
                .andExpect(status().isOk()) ;
    }

    @Test
    @WithMockUser
    void validateNoUser() throws Exception {
        Student mockUser1 = new Student("test1", "test@student.com", "1") ;
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(userService).validateUser(1L);

        mockMvc.perform(post("/user/{id}/validate", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new UserValidationDto("test@test.com", true))))
                .andExpect(status().isNotFound()) ;
    }

    @Test
    @WithMockUser
    void invalidateHappyday() throws Exception {
        Student mockUser1 = new Student("test1", "test@student.com", "1") ;
        doReturn(Optional.of(mockUser1)).when(userRepository).findById(1L) ;

        mockMvc.perform(post("/user/{id}/validate", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new UserValidationDto("test@test.com", false))))
                .andExpect(status().isOk()) ;
    }

    @Test
    @WithMockUser
    void invalidateNoUser() throws Exception {
        Student mockUser1 = new Student("test1", "test@student.com", "1") ;
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(userService).invalidateUser(1L);

        mockMvc.perform(post("/user/{id}/validate", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new UserValidationDto("test@test.com", false))))
                .andExpect(status().isNotFound()) ;
    }


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
