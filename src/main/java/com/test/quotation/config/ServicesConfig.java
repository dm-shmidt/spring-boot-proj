package com.test.quotation.config;

import com.test.quotation.exception.AttachmentFailedException;
import com.test.quotation.exception.NotFoundException;
import com.test.quotation.mapper.CustomerMapper;
import com.test.quotation.mapper.QuotationMapper;
import com.test.quotation.mapper.SubscriptionMapper;
import com.test.quotation.model.dto.CustomerDto;
import com.test.quotation.model.dto.QuotationDto;
import com.test.quotation.model.dto.SubscriptionDto;
import com.test.quotation.model.entity.BaseEntity;
import com.test.quotation.model.entity.Customer;
import com.test.quotation.model.entity.Quotation;
import com.test.quotation.model.entity.Subscription;
import com.test.quotation.repository.base.CustomJpaRepository;
import com.test.quotation.service.BaseService;
import com.test.quotation.util.EntityPatcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServicesConfig {

    @Bean("customerService")
    public BaseService<Customer, CustomerDto> customerService(@Qualifier("customerRepository") CustomJpaRepository<Customer, Long> customerRepository) {
        return new BaseService<>(customerRepository, CustomerMapper.INSTANCE, new EntityPatcher<>()) {
            @Override
            public CustomerDto attachChild(Long id, BaseEntity childEntity) {
                return null;
            }
        };
    }

    @Bean("quotationService")
    public BaseService<Quotation, QuotationDto> quotationService(@Qualifier("quotationRepository") CustomJpaRepository<Quotation, Long> quotationRepository) {
        return new BaseService<>(quotationRepository, QuotationMapper.INSTANCE, new EntityPatcher<>()) {
            @Override
            public QuotationDto attachChild(Long id, BaseEntity childEntity) {
                if (!(childEntity instanceof Customer)) {
                    throw new AttachmentFailedException("Child entity is not of Customer type.");
                }
                Quotation quotation = quotationRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Quotation with id " + id + " not found."));
                quotation.setCustomer((Customer) childEntity);
                quotationRepository.saveAndFlush(quotation);
                quotationRepository.refresh(quotation);
                return QuotationMapper.INSTANCE.toDto(quotation);
            }
        };
    }

    @Bean("subscriptionService")
    public BaseService<Subscription, SubscriptionDto> subscriptionService(@Qualifier("subscriptionRepository") CustomJpaRepository<Subscription, Long> subscriptionRepository) {
        return new BaseService<>(subscriptionRepository, SubscriptionMapper.INSTANCE, new EntityPatcher<>()) {
            @Override
            public SubscriptionDto attachChild(Long id, BaseEntity childEntity) {
                if (!(childEntity instanceof Quotation)) {
                    throw new AttachmentFailedException("Child entity is not of Quotation type.");
                }
                Subscription subscription = subscriptionRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Subscription with id " + id + " not found."));
                subscription.setQuotation((Quotation) childEntity);
                subscriptionRepository.saveAndFlush(subscription);
                subscriptionRepository.refresh(subscription);
                return SubscriptionMapper.INSTANCE.toDto(subscription);
            }
        };
    }

}
