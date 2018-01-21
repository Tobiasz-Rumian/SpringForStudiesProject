package com.klima.projekt1.notification.model.dto;

import com.klima.projekt1.user.model.dto.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDto {
    private UserDto user;
    private String information;
    private String offer;
}
