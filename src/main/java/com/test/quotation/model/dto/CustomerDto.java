package com.test.quotation.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CustomerDto(
        @JsonProperty("id")
        Long id,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("middle_name")
        String middleName,
        @JsonProperty("email")
        String email,
        @JsonProperty("phone_number")
        String phoneNumber,
        @JsonProperty("birth_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthDate
) implements BaseRecord {
}
