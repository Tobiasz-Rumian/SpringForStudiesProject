package com.rumian.projekt1.offer.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OfferDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private double downloadSpeed;
    private double uploadSpeed;
    private long usersCount;
}
