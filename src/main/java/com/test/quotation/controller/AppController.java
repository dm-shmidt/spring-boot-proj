package com.test.quotation.controller;

import com.test.quotation.model.dto.CustomerDto;
import com.test.quotation.model.dto.QuotationDto;
import com.test.quotation.model.dto.SubscriptionDto;
import com.test.quotation.service.CustomerService;
import com.test.quotation.service.QuotationService;
import com.test.quotation.service.SubscriptionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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
        return ResponseEntity.ok(Optional.ofNullable(customerService.getAllCustomers()));
    }

    @GetMapping("customer/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping("customer")
    public ResponseEntity<Object> addCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.addCustomer(customerDto));
    }

    @RequestMapping(value = "customer/{id}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCustomer(@RequestBody Map<String, Object> updates, @PathVariable("id") Long id) {
        return ResponseEntity.ok(customerService.update(updates, id));
    }

    @GetMapping("quotation")
    public ResponseEntity<Object> getAllQuotations() {
        return ResponseEntity.ok(Optional.ofNullable(quotationService.getAllQuotations()));
    }

    @GetMapping("quotation/{id}")
    public ResponseEntity<Object> getQuotationById(@PathVariable Long id) {
        return ResponseEntity.ok(Optional.ofNullable(quotationService.getQuotationById(id)));
    }

    @PostMapping("quotation")
    public ResponseEntity<Object> addOrUpdateExistingQuotation(@RequestBody QuotationDto quotationDto) {
        return ResponseEntity.ok(quotationService.addQuotation(quotationDto));
    }

    @RequestMapping(value = "quotation/{id}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateQuotation(@RequestBody Map<String, Object> updates, @PathVariable("id") Long id) {
        return ResponseEntity.ok(quotationService.update(updates, id));
    }


    @GetMapping("subscription/{id}")
    public ResponseEntity<Object> getSubscription(@PathVariable Long id) {
        return ResponseEntity.ok(Optional.ofNullable(subscriptionService.getSubscriptionById(id)));
    }

    @PostMapping("subscription")
    public ResponseEntity<Object> addOrUpdateExistingSubscription(@RequestBody SubscriptionDto subscriptionDto) {
        return ResponseEntity.ok(subscriptionService.addSubscription(subscriptionDto));
    }

    @RequestMapping(value = "subscription/{id}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateSubscription(@RequestBody Map<String, Object> updates, @PathVariable("id") Long id) {
        return ResponseEntity.ok(subscriptionService.update(updates, id));
    }

}
