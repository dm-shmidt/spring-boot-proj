package com.test.quotation.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Data
@EqualsAndHashCode(exclude = "quotation")
@ToString(exclude = "quotation")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "customer_seq")
    @GenericGenerator(name = "customer_seq", strategy="increment")
    @Column(name = "id", unique = true)
    Long id;

    @Column(name = "firstname")
    @NotNull(message = "Firstname should not be null.")
    private String firstName;

    @Column(name = "lastname")
    @NotNull(message = "Lastname should not be null.")
    private String lastName;

    @Column(name = "middlename")
    @NotNull(message = "Middle name should not be null.")
    private String middleName;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    @Past(message = "Birth date should be past date.")
    private Date birthDate;

    @OneToOne(mappedBy = "customer")
    @Transient
    private Quotation quotation;
}
