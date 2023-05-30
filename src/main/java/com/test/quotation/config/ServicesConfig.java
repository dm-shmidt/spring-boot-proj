package com.test.quotation.config;

import com.test.quotation.model.entity.Customer;
import com.test.quotation.model.entity.Quotation;
import com.test.quotation.model.entity.Subscription;
import com.test.quotation.util.EntityPatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServicesConfig {

    @Bean("customerPatcher")
    public EntityPatcher<Customer> customerEntityPatcher() {
        return new EntityPatcher<>();
    }
    @Bean("quotationPatcher")
    public EntityPatcher<Quotation> quotationEntityPatcher() {
        return new EntityPatcher<>();
    }
    @Bean("subscriptionPatcher")
    public EntityPatcher<Subscription> subscriptionEntityPatcher() {
        return new EntityPatcher<>();
    }
}
