package com.klima.projekt1.user.model.entity;

import com.klima.projekt1.invoice.model.entity.Invoice;
import com.klima.projekt1.offer.model.entity.Offer;
import com.klima.projekt1.user.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @Column(unique = true, length = PESEL_MAX_LENGTH, name = "pesel")
    private long pesel;
    @NotEmpty
    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Please provide a valid e-mail")
    private String email;


    @Column(name = "first_name")
    @NotEmpty(message = "Please provide your first firstName")
    private String firstName;
    @Column(name = "last_name")
    @NotEmpty(message = "Please provide your last firstName")
    private String lastName;
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    private Wallet wallet;

    private Address address;

    @OneToOne
    private Offer offer;

    @OneToMany
    private Set<Invoice> invoices;

    @NotNull
    private Role role;

}
