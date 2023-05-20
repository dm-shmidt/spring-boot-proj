package com.test.quotation.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SubscriptionDto(
        @JsonProperty("id")
        Long id,
        @JsonProperty("start_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date startDate,
        @JsonProperty("valid_until")
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date validUntil,
        @JsonProperty("quotation")
        QuotationDto quotation
) {
}
