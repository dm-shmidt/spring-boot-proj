package com.test.quotation.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SubscriptionDto(
        Long id,
        Date startDate,
        Date validUntil,
        QuotationDto quotation
) {
}
