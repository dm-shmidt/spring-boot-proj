package com.test.quotation.controller;

import com.test.quotation.service.CustomerService;
import com.test.quotation.service.QuotationService;
import com.test.quotation.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AppController {

    private final CustomerService customerService;
    private final QuotationService quotationService;
    private final SubscriptionService subscriptionService;

    public AppController(CustomerService customerService, QuotationService quotationService, SubscriptionService subscriptionService) {
        this.customerService = customerService;
        this.quotationService = quotationService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("customer")
    public ResponseEntity<Object> getAllCustomers() {
        return ResponseEntity.ok().body(Optional.ofNullable(customerService.getAllCustomers()));
    }

    @GetMapping("quotation")
    public ResponseEntity<Object> getAllQuotations() {
        return ResponseEntity.ok().body(Optional.ofNullable(quotationService.getAllQuotations()));
    }

    @GetMapping("quotation/{id}")
    public ResponseEntity<Object> getQuotationById(@PathVariable Long id) {
        return ResponseEntity.ok().body(Optional.ofNullable(quotationService.getQuotationById(id)));
    }

    @GetMapping("subscription/{id}")
    public ResponseEntity<Object> getSubscription(@PathVariable Long id) {
        return ResponseEntity.ok().body(Optional.ofNullable(subscriptionService.getSubscriptionById(id)));
    }
}
