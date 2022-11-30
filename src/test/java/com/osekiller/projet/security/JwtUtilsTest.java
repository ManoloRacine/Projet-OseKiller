package com.osekiller.projet.security;

import com.osekiller.projet.model.user.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class JwtUtilsTest {

    @InjectMocks
    JwtUtils jwtUtils;

    @Test
    void getUsernameFromToken (){
        //Arrange

        Student student = new Student("ayo","ayo@email","123");
        String token = jwtUtils.generateToken(student);

        //Act

        String username = jwtUtils.getUsernameFromToken(token);

        //Assert

        Assertions.assertThat(username)
                .isNotNull()
                .isNotBlank()
                .isEqualTo(student.getUsername());

    }

    @Test
    void getExpirationDateFromToken (){
        //Arrange

        Student student = new Student("ayo","ayo@email","123");
        String token = jwtUtils.generateToken(student);

        //Act

        Date date = jwtUtils.getExpirationDateFromToken(token);

        //Assert

        Assertions.assertThat(date).isNotNull().isAfter(new Date()).isBefore(new Date(System.currentTimeMillis() + JwtUtils.TOKEN_EXPIRATION));

    }

    @Test
    void generateToken (){
        //Arrange

        Student student = new Student("ayo","ayo@email","123");

        //Act

        String token = jwtUtils.generateToken(student);

        //Assert

        Assertions.assertThat(token).isNotNull().isNotBlank();
    }

}
