package com.osekiller.projet.service;

public interface NotificationsService {
    void addNotification(long id, String message);

    void deleteNotification(long userId, long notificationId);

    void addNotificationForRole(String nameRole, String message);
}
