package com.klima.projekt1.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.klima.projekt1.configuration.DatabaseRestrictions.PESEL_MAX_LENGTH;
import static com.klima.projekt1.configuration.DatabaseRestrictions.PHONE_NUMBER_MAX_LENGTH;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {

    @Id
    @NotNull
    @Column(unique = true, name = "id")
    @GeneratedValue()
    private long id;

    @NotBlank(message = "To pole jest wymagane")
    @NotNull(message = "To pole jest wymagane")
    private String street;
    @NotBlank(message = "To pole jest wymagane")
    @NotNull(message = "To pole jest wymagane")
    private String city;
    @NotBlank(message = "To pole jest wymagane")
    @NotNull(message = "To pole jest wymagane")
    private String cityCode;
    @NotNull(message = "To pole jest wymagane")
    @NotBlank(message = "To pole jest wymagane")
    private String houseNumber;
    @NotNull(message = "To pole jest wymagane")
    @NotBlank(message = "To pole jest wymagane")
    private String apartmentNumber;
    @Range(min = 100000000L, max = 999999999L, message = "Numer musi mieć 9 cyfr")
    @Column(length = PHONE_NUMBER_MAX_LENGTH, name = "phoneNumber")
    private long phoneNumber;

}
