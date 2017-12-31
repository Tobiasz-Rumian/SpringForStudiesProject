package com.klima.projekt1.user.model.entity;

import com.klima.projekt1.invoice.model.entity.Invoice;
import com.klima.projekt1.offer.model.entity.Offer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import java.util.Set;

import static com.klima.projekt1.configuration.DatabaseRestrictions.LOGIN_MAX_LENGTH;
import static com.klima.projekt1.configuration.DatabaseRestrictions.PESEL_MAX_LENGTH;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
   @Id
   @NotNull
   @Column(unique = true, length = PESEL_MAX_LENGTH)
   private long pesel;
   @NotNull
   @Column(unique = true, length = LOGIN_MAX_LENGTH)
   private String login;
   @NotEmpty
   private String name;
   @NotEmpty
   private String surname;

   private double passwordHash;

   private double salt;
   @NotNull
   @Column(unique = true)
   @Email
   private String email;
   private Wallet wallet;
   private Address address;
   @OneToOne
   private Offer offer;
   @OneToMany
   private Set<Invoice> invoices;
}
