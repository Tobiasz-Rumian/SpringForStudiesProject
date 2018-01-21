package com.klima.projekt1.notification.service;

import com.klima.projekt1.notification.model.entity.Notification;
import com.klima.projekt1.notification.repository.NotificationRepository;
import com.klima.projekt1.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private NotificationRepository notificationRepository;
    private UserRepository userRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public Notification getNotification(long notificationId) {
        return notificationRepository.findOne(notificationId);
    }

    public List<Notification> getAllUserNotifications(long userId) {
        return notificationRepository.getAllByOwner(userId);
    }

    public List<Notification> getAllUnreedUserNotifications(long userId) {
        return notificationRepository.getAllByOwnerAndSeenIsFalse(userId);
    }

    public List<Notification> getAllUnreedNotifications() {
        List<Notification> notifications = notificationRepository.getAllBySeenIsFalse();
        notifications.forEach(notification -> notification.setSeen(true));
        notificationRepository.save(notifications);
        return notifications;
    }

    public long getNumberOfUnreedUserNotifications(long userId) {
        return notificationRepository.countByOwnerIdAndSeenIsFalse(userId);
    }
}
