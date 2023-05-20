package com.test.quotation.model.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @FieldNameConstants.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Long id;

    @Column(name = "firstname")
    @NotBlank(message = "Firstname should not be empty.")
    private String firstName;

    @Column(name = "lastname")
    @NotBlank(message = "Lastname should not be empty.")
    private String lastName;

    @Column(name = "middlename")
    @Nullable
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
}
