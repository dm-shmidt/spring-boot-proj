package com.test.quotation.service;

import com.test.quotation.exception.AttachmentFailedException;
import com.test.quotation.exception.NotFoundException;
import com.test.quotation.mapper.BaseMapper;
import com.test.quotation.model.dto.SubscriptionDto;
import com.test.quotation.model.entity.BaseEntity;
import com.test.quotation.model.entity.Quotation;
import com.test.quotation.model.entity.Subscription;
import com.test.quotation.repository.base.CustomJpaRepository;
import com.test.quotation.util.EntityPatcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService extends BaseService<Subscription, SubscriptionDto> {
    public SubscriptionService(CustomJpaRepository<Subscription, Long> repository, BaseMapper<Subscription, SubscriptionDto> mapper, @Qualifier("subscriptionPatcher") EntityPatcher<Subscription> patcher) {
        super(repository, mapper, patcher);
    }

    @Override
    public SubscriptionDto attachChild(Long id, BaseEntity childEntity) {
        if (!(childEntity instanceof Quotation)) {
            throw new AttachmentFailedException("Child entity is not of Quotation type.");
        }
        Subscription subscription = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subscription with id " + id + " not found."));
        subscription.setQuotation((Quotation) childEntity);
        repository.saveAndFlush(subscription);
        repository.refresh(subscription);
        return mapper.toDto(subscription);
    }
}
