package com.test.quotation.mapper;

import com.test.quotation.model.dto.SubscriptionDto;
import com.test.quotation.model.entity.Subscription;
import org.mapstruct.Mapper;

@Mapper(uses = {QuotationMapper.class})
public interface SubscriptionMapper extends BaseMapper<Subscription, SubscriptionDto> {

    @Override
    SubscriptionDto toDto(Subscription subscription);

    @Override
    Subscription toEntity(SubscriptionDto subscriptionDto);
}
