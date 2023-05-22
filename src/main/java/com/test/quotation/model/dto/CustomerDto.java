package com.test.quotation.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CustomerDto extends BaseDto {
        @JsonProperty("id")
        private Long id;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
        @JsonProperty("middle_name")
        private String middleName;
        @JsonProperty("email")
        private String email;
        @JsonProperty("phone_number")
        private String phoneNumber;
        @JsonProperty("birth_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDate;
}
