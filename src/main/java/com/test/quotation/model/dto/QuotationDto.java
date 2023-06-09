package com.test.quotation.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record QuotationDto(
        @JsonProperty("id")
        Long id,
        @JsonProperty("beginning_of_insurance")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate beginningOfInsurance,
        @JsonProperty("insured_amount")
        Integer insuredAmount,
        @JsonProperty("date_of_signing_mortgage")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dateOfSigningMortgage,
        @JsonProperty("customer")
        CustomerDto customer
) implements BaseRecord {
}
