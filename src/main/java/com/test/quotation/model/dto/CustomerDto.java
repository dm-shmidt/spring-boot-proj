package com.test.quotation.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.annotation.Nullable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.util.Date;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CustomerDto(
        @NotBlank(message = "Firstname should not be empty.")
        String firstName,
        @NotBlank(message = "Lastname should not be empty.")
        String lastName,
        @Nullable
        String middleName,
        @Email
        String email,
        String phoneNumber,
        @Temporal(TemporalType.DATE)
        @JsonFormat(pattern = "yyyy-MM-dd")
        @Past(message = "Birth date should be past date.")
        Date birthDate)
{ }
