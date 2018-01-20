package com.klima.projekt1.offer.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OfferDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private double downloadSpeed;
    private double uploadSpeed;
    private long usersCount;
}
