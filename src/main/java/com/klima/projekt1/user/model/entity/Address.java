package com.klima.projekt1.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {
    @NotEmpty
    private String street;
    @NotEmpty
    private String city;
    @NotEmpty
    private String cityCode;
    @NotNull
    private Integer apartmentNumber;
    @NotNull
    private Integer houseNumber;
}
