package com.test.quotation.service;

import com.test.quotation.mapper.SubscriptionMapper;
import com.test.quotation.model.dto.SubscriptionDto;
import com.test.quotation.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper mapper = SubscriptionMapper.INSTANCE;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public SubscriptionDto getSubscriptionById(Long id) {
        return mapper.toDto(subscriptionRepository.findById(id)
                .orElseThrow(NoSuchElementException::new));
    }

    public SubscriptionDto addSubscription(SubscriptionDto subscriptionDto) {
        return mapper.toDto(subscriptionRepository.save(mapper.toEntity(subscriptionDto)));
    }
}
