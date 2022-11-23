package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.response.UserDto;
import com.osekiller.projet.service.implementation.AuthServiceImpl;
import com.osekiller.projet.service.implementation.ContractServiceImpl;
import com.osekiller.projet.service.implementation.NotificationsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class NotificationControllerTest {

    @MockBean
    private NotificationsServiceImpl notificationsService;

    @MockBean
    private AuthServiceImpl authService ;

    @Autowired
    private MockMvc mockMvc ;

    @Test
    @WithMockUser()
    void deleteNotificationHappyDay() throws Exception {
        when(authService.getUserFromToken(anyString())).thenReturn(mock(UserDto.class));

        mockMvc.perform(delete("/notifications/{notificationId}/delete-notification", 6)
                        .header(HttpHeaders.AUTHORIZATION,"user-jwt"))
                .andExpect(status().isOk()) ;
    }

    @Test
    @WithMockUser()
    void deleteNotificationNonExistentNotif() throws Exception {
        when(authService.getUserFromToken(anyString())).thenReturn(mock(UserDto.class));
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(notificationsService).deleteNotification(anyLong(), eq(6L));

        mockMvc.perform(delete("/notifications/{notificationId}/delete-notification", 6)
                        .header(HttpHeaders.AUTHORIZATION,"user-jwt"))
                .andExpect(status().isNotFound()) ;
    }

    @Test
    @WithMockUser()
    void getNotificationsHappyDay() throws Exception {
        when(authService.getUserFromToken(anyString())).thenReturn(mock(UserDto.class));

        mockMvc.perform(get("/notifications")
                        .header(HttpHeaders.AUTHORIZATION,"user-jwt"))
                .andExpect(status().isOk()) ;
    }
}
