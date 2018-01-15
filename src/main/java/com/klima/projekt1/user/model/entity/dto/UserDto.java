package com.klima.projekt1.user.model.entity.dto;

import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String surname;
    private long pesel;
    private long phoneNumber;
    private String street;
    private int homeNumber;
    private int apartmentNumber;
    private String city;
    private String cityCode;
    private String email;
    private String password;
    private boolean declaration;
}
