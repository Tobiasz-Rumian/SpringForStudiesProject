package com.rumian.projekt1.notification.repository;

import com.rumian.projekt1.notification.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> getAllByOwner(long ownerId);

    List<Notification> getAllByOwnerAndSeenIsFalse(long ownerId);

    List<Notification> getAllBySeenIsFalse();

    long countAllBySeenIsFalse();
    long countByOwner(long user);

}
