package com.test.quotation.mapper;

import com.test.quotation.model.dto.QuotationDto;
import com.test.quotation.model.entity.Quotation;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {CustomerMapper.class})
public interface QuotationMapper extends BaseMapper<Quotation, QuotationDto> {
    QuotationMapper INSTANCE = Mappers.getMapper(QuotationMapper.class);

    @Override
    @Named("toQuotationDto")
    QuotationDto toDto(Quotation quotation);

    @Override
    Quotation toEntity(QuotationDto quotationDto);

    @Override
    @IterableMapping(qualifiedByName = "toQuotationDto")
    List<QuotationDto> toDtoList(List<Quotation> quotations);
}
