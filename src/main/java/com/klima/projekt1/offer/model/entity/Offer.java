package com.klima.projekt1.offer.model.entity;

import com.klima.projekt1.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Offer {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User owner;
    @NotEmpty
    private String offerName;
    @NotNull
    private BigDecimal price;
    private double downloadSpeed;
    private double uploadSpeed;
}
