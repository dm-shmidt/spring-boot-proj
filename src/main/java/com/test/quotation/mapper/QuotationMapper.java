package com.test.quotation.mapper;

import com.test.quotation.model.dto.QuotationDto;
import com.test.quotation.model.entity.Quotation;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {CustomerMapper.class})
public interface QuotationMapper {
    QuotationMapper INSTANCE = Mappers.getMapper(QuotationMapper.class);

    @Named("toQuotationDto")
    QuotationDto toDto(Quotation quotation);

    Quotation toEntity(QuotationDto quotationDto);

    @IterableMapping(qualifiedByName = "toQuotationDto")
    List<QuotationDto> toQuotationDtoList(List<Quotation> quotations);

}
