package com.test.quotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotationApplication.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(CustomerRepository repository) {
//        return args -> {
//
//            LocalDate localDate = LocalDate.of(2000, 10, 01);
//            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//            repository.save(
//                    new Customer(0L,
//                            "John",
//                            "Manning",
//                            "",
//                            "j.man@test.com",
//                            "8-123-654-987-879",
//                            date
//                    ));
//            repository.save(
//                    new Customer(0L,
//                            "Peter",
//                            "Manning",
//                            "",
//                            "p.man@test.com",
//                            "8-654-222-923-879",
//                            date
//                    ));
//
//        };
//    }
}
