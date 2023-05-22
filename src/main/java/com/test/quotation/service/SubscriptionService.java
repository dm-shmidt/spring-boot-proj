package com.test.quotation.service;

import com.test.quotation.config.Patcher;
import com.test.quotation.exception.NotFoundException;
import com.test.quotation.exception.UpdateFailedException;
import com.test.quotation.mapper.SubscriptionMapper;
import com.test.quotation.model.dto.SubscriptionDto;
import com.test.quotation.model.entity.Subscription;
import com.test.quotation.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper mapper = SubscriptionMapper.INSTANCE;
    private final Patcher patcher;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, Patcher patcher) {
        this.subscriptionRepository = subscriptionRepository;
        this.patcher = patcher;
    }

    public SubscriptionDto getSubscriptionById(Long id) {
        return mapper.toDto(subscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subscription with id " + id + " not found.")));
    }

    public SubscriptionDto addSubscription(SubscriptionDto subscriptionDto) {
        return mapper.toDto(subscriptionRepository.save(mapper.toEntity(subscriptionDto)));
    }

    public SubscriptionDto update(Map<String, Object> updates, Long id) {
        Subscription subscriptionFromDB = subscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subscription with id " + id + " not found."));

        SubscriptionDto subscriptionDto = mapper.toDto(subscriptionFromDB);
        try {
            subscriptionDto = (SubscriptionDto) patcher.patch(updates, subscriptionDto);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new UpdateFailedException("Patch failed.");
        }

        subscriptionFromDB = mapper.toEntity(subscriptionDto);

        return mapper.toDto(subscriptionRepository.save(subscriptionFromDB));

    }
}
