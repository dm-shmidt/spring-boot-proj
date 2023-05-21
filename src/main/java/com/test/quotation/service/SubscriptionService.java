package com.test.quotation.service;

import com.test.quotation.exception.NotFoundException;
import com.test.quotation.mapper.SubscriptionMapper;
import com.test.quotation.model.dto.SubscriptionDto;
import com.test.quotation.model.entity.Subscription;
import com.test.quotation.repository.SubscriptionRepository;
import com.test.quotation.util.PropsMapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper mapper = SubscriptionMapper.INSTANCE;
    private final PropsMapper<Subscription> propsMapper = new PropsMapper<>();

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

    public SubscriptionDto update(Map<String, Object> updates, Long id) {
        Subscription subscriptionFromDB = subscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subscription with id " + id + " not found."));

        propsMapper.updateValues(updates, subscriptionFromDB);

        subscriptionRepository.save(subscriptionFromDB);

        return mapper.toDto(subscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DB error. Subscription with id " + id + " not found.")));

    }
}
