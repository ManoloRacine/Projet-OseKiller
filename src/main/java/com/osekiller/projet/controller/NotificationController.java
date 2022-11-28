package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.response.NotificationDto;
import com.osekiller.projet.service.AuthService;
import com.osekiller.projet.service.NotificationsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/notifications")
public class NotificationController {

    NotificationsService notificationsService ;

    AuthService authService ;


    @GetMapping()
    public ResponseEntity<List<NotificationDto>> getNotifications(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        String jwt = header.substring(7) ;
        long userId = authService.getUserFromToken(jwt).id();

        return ResponseEntity.ok(notificationsService.getNotifications(userId)) ;
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        String jwt = header.substring(7) ;
        long userId = authService.getUserFromToken(jwt).id();

        notificationsService.deleteNotification(userId, notificationId);

        return ResponseEntity.ok().build() ;
    }
}
