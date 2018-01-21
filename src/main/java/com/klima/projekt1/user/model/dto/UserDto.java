package com.klima.projekt1.user.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String offerName;
    private String firstName;
    private String lastName;
    private long phoneNumber;
    private String email;
    private String address;
    private long pesel;
    private boolean haveDebt;
}
