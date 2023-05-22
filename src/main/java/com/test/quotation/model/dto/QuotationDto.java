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
public class QuotationDto extends BaseDto {
        @JsonProperty("id")
        private Long id;
        @JsonProperty("beginning_of_insurance")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate beginningOfInsurance;
        @JsonProperty("insured_amount")
        private Integer insuredAmount;
        @JsonProperty("date_of_signing_mortgage")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate dateOfSigningMortgage;
        @JsonProperty("customer")
        private CustomerDto customer;

}
