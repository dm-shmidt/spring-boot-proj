package com.test.quotation.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record QuotationDto(
        Long id,
        Date beginningOfInsurance,
        Integer insuredAmount,
        Date dateOfSigningMortgage,
        CustomerDto customer
) {
}
