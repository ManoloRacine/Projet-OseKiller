package com.osekiller.projet.controller.payload.response;

import com.osekiller.projet.model.Notification;

import javax.validation.constraints.NotNull;

public record NotificationDto(
        @NotNull Long notificationId,
        @NotNull String notificationMessage,
        @NotNull String notificationDate
        
) {
    public static NotificationDto from(Notification notification){
        return new NotificationDto(
                notification.getId(),
                notification.getMessage(),
                notification.getTimeStamp().toString()
        );
    }
}
