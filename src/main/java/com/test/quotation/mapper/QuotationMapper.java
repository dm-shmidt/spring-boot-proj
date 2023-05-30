package com.test.quotation.mapper;

import com.test.quotation.model.dto.QuotationDto;
import com.test.quotation.model.entity.Quotation;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(uses = {CustomerMapper.class})
public interface QuotationMapper extends BaseMapper<Quotation, QuotationDto> {

    @Override
    @Named("toQuotationDto")
    QuotationDto toDto(Quotation quotation);

    @Override
    Quotation toEntity(QuotationDto quotationDto);

    @Override
    @IterableMapping(qualifiedByName = "toQuotationDto")
    List<QuotationDto> toDtoList(List<Quotation> quotations);
}
