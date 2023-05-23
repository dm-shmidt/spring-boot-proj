package com.test.quotation.service;

import com.test.quotation.exception.NotFoundException;
import com.test.quotation.mapper.SubscriptionMapper;
import com.test.quotation.model.dto.SubscriptionDto;
import com.test.quotation.model.entity.Quotation;
import com.test.quotation.model.entity.Subscription;
import com.test.quotation.repository.SubscriptionRepository;
import com.test.quotation.util.EntityPatcher;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper mapper = SubscriptionMapper.INSTANCE;

    private final EntityPatcher<Subscription> entityPatcher = new EntityPatcher<>();

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public SubscriptionDto getSubscriptionById(Long id) {
        return mapper.toDto(subscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subscription with id " + id + " not found.")));
    }

    public SubscriptionDto addSubscription(SubscriptionDto subscriptionDto) {
        Subscription subscription = subscriptionRepository.saveAndFlush(mapper.toEntity(subscriptionDto));
        subscriptionRepository.refresh(subscription);
        return mapper.toDto(subscription);
    }

    public SubscriptionDto update(Map<String, Object> updates, Long id) {
        Subscription subscriptionFromDB = subscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subscription with id " + id + " not found."));

        subscriptionFromDB = entityPatcher.patch(updates, subscriptionFromDB);

        return mapper.toDto(subscriptionRepository.save(subscriptionFromDB));
    }

    public SubscriptionDto attachQuotation(Long subscriptionId, Quotation quotation) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new NotFoundException("Subscription with id " + subscriptionId + " not found."));
        subscription.setQuotation(quotation);
        subscriptionRepository.saveAndFlush(subscription);
        subscriptionRepository.refresh(subscription);
        return mapper.toDto(subscription);
    }
}
