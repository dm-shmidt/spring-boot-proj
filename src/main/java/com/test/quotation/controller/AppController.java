package com.test.quotation.controller;

import com.test.quotation.model.dto.CustomerDto;
import com.test.quotation.model.dto.QuotationDto;
import com.test.quotation.model.dto.SubscriptionDto;
import com.test.quotation.service.CustomerService;
import com.test.quotation.service.QuotationService;
import com.test.quotation.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("customer")
    public ResponseEntity<Object> addCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok().body(customerService.addCustomer(customerDto));
    }

    @GetMapping("quotation")
    public ResponseEntity<Object> getAllQuotations() {
        return ResponseEntity.ok().body(Optional.ofNullable(quotationService.getAllQuotations()));
    }

    @GetMapping("quotation/{id}")
    public ResponseEntity<Object> getQuotationById(@PathVariable Long id) {
        return ResponseEntity.ok().body(Optional.ofNullable(quotationService.getQuotationById(id)));
    }

    @PostMapping("quotation")
    public ResponseEntity<Object> addOrUpdateExistingQuotation(@RequestBody QuotationDto quotationDto) {
        return ResponseEntity.ok().body(quotationService.addQuotation(quotationDto));
    }

    @GetMapping("subscription/{id}")
    public ResponseEntity<Object> getSubscription(@PathVariable Long id) {
        return ResponseEntity.ok().body(Optional.ofNullable(subscriptionService.getSubscriptionById(id)));
    }

    @PostMapping("subscription")
    public ResponseEntity<Object> addOrUpdateExistingSubscription(@RequestBody SubscriptionDto subscriptionDto) {
        return ResponseEntity.ok().body(subscriptionService.addSubscription(subscriptionDto));
    }
}
