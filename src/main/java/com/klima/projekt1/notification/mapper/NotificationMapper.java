package com.klima.projekt1.notification.mapper;

import com.klima.projekt1.notification.model.dto.NotificationDto;
import com.klima.projekt1.notification.model.entity.Notification;
import com.klima.projekt1.user.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public class NotificationMapper {
    @Getter(AccessLevel.PACKAGE)
    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<NotificationDto> toNotificationDtos(List<Notification> notifications) {
        List<NotificationDto> notificationDtos = new ArrayList<>();
        String offer;
        for (Notification notification : notifications) {
            offer = notification.getNotificationCode().isOfferRelated()
                    ? ""
                    : notification.getOwner().getOffer().getName();

            notificationDtos.add(NotificationDto.builder().user(userMapper.toUserDto(notification.getOwner()))
                    .information(notification.getNotificationCode().getMessage())
                    .offer(offer).build());
        }
        return notificationDtos;
    }
}
