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
public class SubscriptionDto extends BaseDto {
        @JsonProperty("id")
        Long id;
        @JsonProperty("start_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate startDate;
        @JsonProperty("valid_until")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate validUntil;
        @JsonProperty("quotation")
        QuotationDto quotation;
}
