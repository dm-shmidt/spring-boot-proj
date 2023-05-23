package com.test.quotation;

import com.test.quotation.repository.base.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class QuotationApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuotationApplication.class, args);
    }
}
