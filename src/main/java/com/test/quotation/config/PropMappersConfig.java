package com.test.quotation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropMappersConfig {

    @Bean
    public Patcher getPatcher() {
        return new Patcher();
    }
}
