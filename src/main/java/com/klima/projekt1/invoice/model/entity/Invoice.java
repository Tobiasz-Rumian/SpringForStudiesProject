package com.klima.projekt1.invoice.model.entity;

import com.klima.projekt1.user.model.entity.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@EqualsAndHashCode(exclude = "owner")
@ToString(exclude = "owner")
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

    public String getIssueDateAsText() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(issueDate);
    }

    public String getPaymentDeadlineAsText() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(paymentDeadline);
    }

    public String getPaymentDateAsText() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(paymentDate);
    }
}
