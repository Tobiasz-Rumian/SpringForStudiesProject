package com.rumian.projekt1.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseRestrictions {
    public static final int EVENT_TITLE_MAX_LENGTH = 100;
    public static final int EVENT_DESCRIPTION_MAX_LENGTH = 1000;
    public static final int EVENT_LOCATION_MAX_LENGTH = 100;
    public static final int EVENT_CAPACITY_MAX_VALUE = 1000;
    public static final int LOGIN_MAX_LENGTH = 50;
    public static final int ROLE_NAME_MAX_LENGTH = 50;
    public static final int USER_UUID_MAX_LENGTH = 35;
    public static final int PESEL_MAX_LENGTH = 11;
    public static final int PHONE_NUMBER_MAX_LENGTH = 9;


}
