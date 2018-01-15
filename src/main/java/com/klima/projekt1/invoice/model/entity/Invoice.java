package com.klima.projekt1.invoice.model.entity;

import com.klima.projekt1.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue
    private long invoiceNumber;
    @OneToOne
    private User owner;
    @NotNull
    private BigDecimal price;
    @NotNull
    @CreatedDate
    private ZonedDateTime issueDate;
    @NotNull
    private ZonedDateTime paymentDeadline;
    @NotNull
    private ZonedDateTime paymentDate;
}
