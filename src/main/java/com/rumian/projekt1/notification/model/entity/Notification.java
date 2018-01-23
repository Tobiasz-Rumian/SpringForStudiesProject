package com.rumian.projekt1.notification.model.entity;

import com.rumian.projekt1.notification.model.enums.NotificationCode;
import com.rumian.projekt1.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications")
public class Notification {

    @Id
    @NotNull
    @Column(unique = true, name = "id")
    @GeneratedValue()
    private long id;

    @NotNull
    @ManyToOne
    private User owner;
    @NotNull
    private NotificationCode notificationCode;
    private boolean seen;
}
