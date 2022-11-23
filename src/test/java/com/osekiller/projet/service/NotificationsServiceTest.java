package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.response.NotificationDto;
import com.osekiller.projet.model.ERole;
import com.osekiller.projet.model.Notification;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.Role;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.model.user.Manager;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.model.user.User;
import com.osekiller.projet.repository.user.RoleRepository;
import com.osekiller.projet.repository.user.UserRepository;
import com.osekiller.projet.service.implementation.NotificationsServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class NotificationsServiceTest {

    @Mock
    UserRepository userRepository ;

    @Mock
    RoleRepository roleRepository ;


    @InjectMocks
    NotificationsServiceImpl notificationsService ;


    static Student student;

    static Manager manager1 ;

    static Manager manager2 ;

    @BeforeEach
    void beforeEach(){
        student = new Student("student","student@email.com","123");
        student.setId(5L);
        manager1 = new Manager("manager1","manager1@email.com","123");
        manager1.setId(6L);
        manager2 = new Manager("manager2","manager2@email.com","123");
        manager2.setId(7L);
    }

    @Test
    void addNotificationHappyDay() {
        //Arrange
        when(userRepository.findById(5L)).thenReturn(Optional.ofNullable(student)) ;

        //Act
        notificationsService.addNotification(5L, "test") ;

        //Assert
        assertThat(student.getNotifications().size()).isEqualTo(1) ;
        assertThat(student.getNotifications().get(0).getMessage()).isEqualTo("test") ;
    }

    @Test
    void addNotificationsHappyDay() {
        //Arrange
        when(userRepository.findById(5L)).thenReturn(Optional.ofNullable(student)) ;

        //Act
        notificationsService.addNotification(5L, "test1") ;
        notificationsService.addNotification(5L, "test2") ;
        notificationsService.addNotification(5L, "test3") ;

        //Assert
        assertThat(student.getNotifications().size()).isEqualTo(3) ;
        assertThat(student.getNotifications().get(0).getMessage()).isEqualTo("test1") ;
        assertThat(student.getNotifications().get(1).getMessage()).isEqualTo("test2") ;
        assertThat(student.getNotifications().get(2).getMessage()).isEqualTo("test3") ;
    }

    @Test
    void addNotificationsUserNotFound() {
        //Arrange
        when(userRepository.findById(5L)).thenReturn(Optional.empty()) ;

        //Act & Assert
        assertThatThrownBy(() -> notificationsService.addNotification(5L, "test"))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status")
                .isEqualTo(HttpStatus.NOT_FOUND) ;


    }

    @Test
    void deleteNotificationHappyDay() {
        //Arrange
        student.setNotifications(new ArrayList<>(List.of(Notification.builder().id(1L).build(),
                Notification.builder().id(2L).build(),
                Notification.builder().id(3L).build())));
        when(userRepository.findById(5L)).thenReturn(Optional.ofNullable(student)) ;

        //Act
        notificationsService.deleteNotification(5L, 2L) ;

        //Assert
        assertThat(student.getNotifications().size()).isEqualTo(2) ;
        assertThat(student.getNotifications().get(0).getId()).isEqualTo(1L) ;
        assertThat(student.getNotifications().get(1).getId()).isEqualTo(3L) ;
    }

    @Test
    void deleteNotificationNotFound() {
        //Arrange
        student.setNotifications(new ArrayList<>(List.of(Notification.builder().id(1L).build(),
                Notification.builder().id(2L).build(),
                Notification.builder().id(3L).build())));
        when(userRepository.findById(5L)).thenReturn(Optional.ofNullable(student)) ;

        //Act & Assert
        assertThatThrownBy(() -> notificationsService.deleteNotification(5L, 1111L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status")
                .isEqualTo(HttpStatus.NOT_FOUND) ;
    }

    @Test
    void deleteNotificationUserNotFound() {
        //Arrange
        student.setNotifications(new ArrayList<>(List.of(Notification.builder().id(1L).build(),
                Notification.builder().id(2L).build(),
                Notification.builder().id(3L).build())));
        when(userRepository.findById(5L)).thenReturn(Optional.empty()) ;

        //Act & Assert
        assertThatThrownBy(() -> notificationsService.deleteNotification(5L, 1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status")
                .isEqualTo(HttpStatus.NOT_FOUND) ;
    }

    @Test
    void addNotificationForRoleHappyDay() {
        //Arrange
        Role role = new Role(ERole.MANAGER.name()) ;
        when(userRepository.findAllByRole(role)).thenReturn(List.of(manager1, manager2)) ;
        when(roleRepository.findByName(ERole.MANAGER.name())).thenReturn(Optional.of(role)) ;

        //Act
        notificationsService.addNotificationForRole(ERole.MANAGER.name(), "message") ;

        //Assert
        assertThat(manager1.getNotifications()).isEqualTo(manager2.getNotifications()) ;
        assertThat(student.getNotifications().size()).isEqualTo(0) ;
    }

    @Test
    void addNotificationsForRoleHappyDay() {
        //Arrange
        Role role = new Role(ERole.MANAGER.name()) ;
        when(userRepository.findAllByRole(role)).thenReturn(List.of(manager1, manager2)) ;
        when(roleRepository.findByName(ERole.MANAGER.name())).thenReturn(Optional.of(role)) ;

        //Act
        notificationsService.addNotificationForRole(ERole.MANAGER.name(), "message1") ;
        notificationsService.addNotificationForRole(ERole.MANAGER.name(), "message2") ;
        notificationsService.addNotificationForRole(ERole.MANAGER.name(), "message3") ;

        //Assert
        assertThat(manager1.getNotifications()).isEqualTo(manager2.getNotifications()) ;
        assertThat(student.getNotifications().size()).isEqualTo(0) ;
    }

    @Test
    void addNotificationForRoleNotFound() {
        //Arrange
        when(roleRepository.findByName(any())).thenReturn(Optional.empty()) ;

        //Act & Assert
        assertThatThrownBy(() -> notificationsService.addNotificationForRole(ERole.MANAGER.name(), "message"))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status")
                .isEqualTo(HttpStatus.NOT_FOUND) ;
    }

    @Test
    void getNotificationsHappyDay() {
        //Arrange
        User user = mock(User.class) ;
        when(userRepository.findById(5L)).thenReturn(Optional.ofNullable(user)) ;
        Notification notification1 = mock(Notification.class) ;
        Notification notification2 = mock(Notification.class) ;
        Notification notification3 = mock(Notification.class) ;
        List<Notification> notifications = List.of(notification1, notification2, notification3) ;
        for (Notification notification: notifications
             ) {
            when(notification.getTimeStamp()).thenReturn(LocalDateTime.now()) ;
        }
        when(user.getNotifications()).thenReturn(notifications) ;

        //Act
        List<NotificationDto> notificationsDto = notificationsService.getNotifications(5L) ;

        //Assert
        assertThat(notificationsDto.size()).isEqualTo(3) ;
    }

    @Test
    void getNotificationsEmpty() {
        //Arrange
        User user = mock(User.class) ;
        when(userRepository.findById(5L)).thenReturn(Optional.ofNullable(user)) ;
        when(user.getNotifications()).thenReturn(new ArrayList<>()) ;

        //Act
        List<NotificationDto> notifications = notificationsService.getNotifications(5L) ;

        //Assert
        assertThat(notifications).isNotNull() ;
        assertThat(notifications.size()).isEqualTo(0) ;
    }

    @Test
    void getNotificationNonExistentUser() {
        //Arrange
        when(userRepository.findById(5L)).thenReturn(Optional.empty()) ;

        //Act & Assert
        assertThatThrownBy(() -> notificationsService.getNotifications(5L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting("status")
                .isEqualTo(HttpStatus.NOT_FOUND) ;
    }
}
