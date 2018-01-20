package com.klima.projekt1.user.model.entity;

import com.klima.projekt1.invoice.model.entity.Invoice;
import com.klima.projekt1.offer.model.entity.Offer;
import com.klima.projekt1.user.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

import static com.klima.projekt1.configuration.DatabaseRestrictions.PESEL_MAX_LENGTH;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @NotNull
    @Column(unique = true, name = "id")
    @GeneratedValue()
    private long id;

    @Column(unique = true, length = PESEL_MAX_LENGTH, name = "pesel")
    @Range(min = 10000000000L, max = 99999999999L, message = "Pesel musi mieć 11 cyfr")
    private long pesel;

    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Błędny email")
    private String email;

    @Column(name = "first_name")
    @NotBlank(message = "Wprowadź swoje imie")
    private String firstName;
    @Column(name = "last_name")
    @NotBlank(message = "Wprowadź swoje nazwisko")
    private String lastName;
    @NotBlank(message = "To pole jest wymagane")
    @Column(name = "password")
    @Length(min = 8, message = "Hasło powinno zawierać minimum 8 znaków")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToOne(cascade=CascadeType.ALL)
    private Address address;

    @ManyToOne
    private Offer offer;

    @OneToMany
    private Set<Invoice> invoices;

    @NotNull
    private Role role = Role.USER;

    @NotNull
    @Column(name = "money")
    private BigDecimal money = new BigDecimal(0);

}
