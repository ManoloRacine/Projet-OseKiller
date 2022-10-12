package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.response.UserDto;
import com.osekiller.projet.model.Role;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.model.user.Manager;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.model.user.User;
import com.osekiller.projet.repository.user.UserRepository;
import com.osekiller.projet.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService ;

    @Mock
    UserRepository userRepository ;

    @Test
    void getUsersHappyDay() {
        //Arrange
        Student mockUser1 = new Student("test1", "test@student.com", "1") ;
        mockUser1.setRole(new Role("STUDENT"));
        UserDto mockDto1 = new UserDto(mockUser1.getEmail(), mockUser1.getName(), mockUser1.isEnabled(), mockUser1.getId(), mockUser1.getRole().getName()) ;
        Manager mockUser2 = new Manager("test2", "test@manager.com", "1") ;
        mockUser2.setRole(new Role("MANAGER"));
        UserDto mockDto2 = new UserDto(mockUser2.getEmail(), mockUser2.getName(), mockUser2.isEnabled(), mockUser2.getId(), mockUser2.getRole().getName()) ;
        Company mockUser3 = new Company("test3", "test@company.com", "1") ;
        mockUser3.setRole(new Role("COMPANY"));
        UserDto mockDto3 = new UserDto(mockUser3.getEmail(), mockUser3.getName(), mockUser3.isEnabled(), mockUser3.getId(), mockUser3.getRole().getName()) ;
        List<User> mockList = new ArrayList<>(Arrays.asList(mockUser1, mockUser2, mockUser3)) ;
        List<UserDto> mockDtoList = new ArrayList<UserDto>(Arrays.asList(mockDto1, mockDto2, mockDto3)) ;
        doReturn(mockList).when(userRepository).findAll() ;

        //Act
        List<UserDto> list = userService.getUsers() ;

        //Assert
        Assertions.assertNotNull(list);
        Assertions.assertSame(3, list.size());
        Assertions.assertEquals(mockDtoList, list);
    }

    @Test
    void getUsersEmpty() {
        //Arrange
        doReturn(new ArrayList<User>()).when(userRepository).findAll() ;

        //Act
        List<UserDto> list = userService.getUsers() ;

        //Assert
        Assertions.assertNotNull(list);
        Assertions.assertSame(0, list.size());
    }

    @Test
    void validateUserHappyDay() {
        //Arrange
        Student mockUser1 = new Student("test1", "test@student.com", "1") ;
        doReturn(Optional.of(mockUser1)).when(userRepository).findById(any()) ;

        //Act
        userService.validateUser(1L);

        //Assert
        Assertions.assertTrue(mockUser1.isEnabled());
        verify(userRepository).save(mockUser1) ;
    }

    @Test
    void validateUserNoUser() {
        //Arrange
        doReturn(Optional.empty()).when(userRepository).findById(any()) ;

        //Act & Assert
        assertThatThrownBy(() -> userService.validateUser(1L)).isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    void invalidateUserHappyDay() {
        //Arrange
        Student mockUser1 = new Student("test1", "test@student.com", "1") ;
        doReturn(Optional.of(mockUser1)).when(userRepository).findById(any()) ;

        //Act
        userService.invalidateUser(1L);

        //Assert
        verify(userRepository).delete(mockUser1); ;
    }

    @Test
    void invalidateUserNoUser() {
        //Arrange
        doReturn(Optional.empty()).when(userRepository).findById(any()) ;

        //Act & Assert
        assertThatThrownBy(() -> userService.invalidateUser(1L)).isInstanceOf(ResponseStatusException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);

    }
}
