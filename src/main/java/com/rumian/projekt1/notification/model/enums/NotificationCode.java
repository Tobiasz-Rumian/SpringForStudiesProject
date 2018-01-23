package com.rumian.projekt1.notification.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationCode {
    USER_CHOOSE_OFFER(" wybrał/a ofertę: ", true),
    USER_RESIGN_FROM_OFFER(" zrezygnował z oferty: ", true),
    USER_CHANGED_PERSONAL_DATA(" zmienił dane osobowe (cesja)", false);
    private final String message;
    private final boolean offerRelated;
}
