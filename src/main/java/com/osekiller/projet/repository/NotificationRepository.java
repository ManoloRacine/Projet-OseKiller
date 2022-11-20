package com.osekiller.projet.repository;

import com.osekiller.projet.model.Notification;
import com.osekiller.projet.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
