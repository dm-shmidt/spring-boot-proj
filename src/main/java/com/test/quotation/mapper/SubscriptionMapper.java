package com.test.quotation.mapper;

import com.test.quotation.model.dto.SubscriptionDto;
import com.test.quotation.model.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {QuotationMapper.class})
public interface SubscriptionMapper {

    SubscriptionMapper INSTANCE = Mappers.getMapper(SubscriptionMapper.class);

    SubscriptionDto toDto(Subscription subscription);

    Subscription toEntity(SubscriptionDto subscriptionDto);
}
