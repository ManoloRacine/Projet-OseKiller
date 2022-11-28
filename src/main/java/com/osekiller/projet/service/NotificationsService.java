package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.response.NotificationDto;

import java.util.List;

public interface NotificationsService {
    void addNotification(long id, String message);

    void deleteNotification(long userId, long notificationId);

    void addNotificationForRole(String nameRole, String message);

    List<NotificationDto> getNotifications(long studentId);
}
