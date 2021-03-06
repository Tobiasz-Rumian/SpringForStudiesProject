package com.rumian.projekt1.user.model.entity;

import com.rumian.projekt1.invoice.model.entity.Invoice;
import com.rumian.projekt1.notification.model.entity.Notification;
import com.rumian.projekt1.offer.model.entity.Offer;
import com.rumian.projekt1.user.enums.Role;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import static com.rumian.projekt1.configuration.DatabaseRestrictions.PESEL_MAX_LENGTH;

@Entity
@Data
@EqualsAndHashCode(exclude = "invoices")
@ToString(exclude = "invoices")
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

    @ManyToOne(cascade=CascadeType.ALL)
    private Offer offer;

    @OneToMany
    private Set<Invoice> invoices;

    @NotNull
    private Role role = Role.USER;

    @NotNull
    @Column(name = "money")
    private BigDecimal money = new BigDecimal(0);

    private ZonedDateTime payDate;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Notification> notifications = new HashSet<>();

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getPayDateAsText() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(payDate);
    }
}
