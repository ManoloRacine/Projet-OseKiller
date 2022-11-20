package com.osekiller.projet.service.implementation;

import com.osekiller.projet.model.Notification;
import com.osekiller.projet.model.Role;
import com.osekiller.projet.model.user.User;
import com.osekiller.projet.repository.user.RoleRepository;
import com.osekiller.projet.repository.user.UserRepository;
import com.osekiller.projet.service.NotificationsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationsServiceImpl implements NotificationsService {
    UserRepository userRepository ;
    RoleRepository roleRepository ;

    @Override
    public void addNotification(long id, String message) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;

        Notification notification = Notification.builder()
                .message(message)
                .timeStamp(LocalDateTime.now())
                .build() ;

        user.getNotifications().add(notification);

        userRepository.save(user) ;
    }



    @Override
    public void deleteNotification(long userId, long notificationId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;

        if (user.getNotifications().stream().map(Notification::getId).toList().contains(notificationId)) {
            user.getNotifications().removeIf(notification -> notification.getId() == notificationId) ;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;
        }

        userRepository.save(user) ;
    }

    @Override
    public void addNotificationForRole(String nameRole, String message) {
        Role role = roleRepository.findByName(nameRole).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;
        List<User> users = userRepository.findAllByRole(role) ;

        Notification notification = Notification.builder()
                .message(message)
                .timeStamp(LocalDateTime.now())
                .build() ;

        users.forEach(user -> {
            user.getNotifications().add(notification) ;
            userRepository.save(user) ;
        });


    }
}
