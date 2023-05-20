package com.test.quotation.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;

import java.util.Date;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CustomerDto(
        String firstName,
        String lastName,
        String middleName,
        String email,
        String phoneNumber,
        Date birthDate)
{ }
