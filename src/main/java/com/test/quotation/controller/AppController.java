package com.test.quotation.controller;

import com.test.quotation.model.dto.AttachRequestDto;
import com.test.quotation.model.dto.CustomerDto;
import com.test.quotation.model.dto.QuotationDto;
import com.test.quotation.model.dto.SubscriptionDto;
import com.test.quotation.service.CustomerService;
import com.test.quotation.service.QuotationService;
import com.test.quotation.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@Transactional
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
        return ResponseEntity.ok(customerService.getCustomerDtoById(id));
    }

    @PostMapping("customer")
    public ResponseEntity<Object> addCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.addCustomer(customerDto));
    }

    @PatchMapping(value = "customer/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody Map<String, Object> updates, @PathVariable("id") Long id) {
        return ResponseEntity.ok(customerService.update(updates, id));
    }

    @GetMapping("quotation")
    public ResponseEntity<Object> getAllQuotations() {
        return ResponseEntity.ok(Optional.ofNullable(quotationService.getAllQuotations()));
    }

    @GetMapping("quotation/{id}")
    public ResponseEntity<Object> getQuotationById(@PathVariable Long id) {
        return ResponseEntity.ok(Optional.ofNullable(quotationService.getQuotationDtoById(id)));
    }

    @PostMapping("quotation")
    public ResponseEntity<Object> addOrUpdateExistingQuotation(@RequestBody QuotationDto quotationDto) {
        QuotationDto newQuotationDto = quotationService.addQuotation(quotationDto);
        CustomerDto customerDto = newQuotationDto.getCustomer();
        if (customerDto != null) {
            newQuotationDto.setCustomer(customerService.getCustomerDtoById(customerDto.getId()));
        }
        return ResponseEntity.ok(newQuotationDto);
    }

    @PatchMapping(value = "quotation/{id}")
    public ResponseEntity<?> updateQuotation(@RequestBody Map<String, Object> updates, @PathVariable("id") Long id) {
        return ResponseEntity.ok(quotationService.update(updates, id));
    }

    @PutMapping("quotation/attach_customer")
    public ResponseEntity<?> attachCustomerToQuotation(@RequestBody AttachRequestDto attachRequestDto) {
        QuotationDto quotationDto = quotationService.getQuotationDtoById(attachRequestDto.getParentId());

        if (quotationDto.getCustomer() != null) {
            return ResponseEntity.accepted().body(
                    Map.of("error", "Cannot be attached.",
                            "message", "Quotation with id "
                            + attachRequestDto.getParentId() + " already owns customer with id " + attachRequestDto.getChildId()));
        }

        return ResponseEntity.ok(quotationService.attachCustomer(attachRequestDto.getParentId(),
                customerService.getCustomerById(attachRequestDto.getChildId())));
    }

    @GetMapping("subscription/{id}")
    public ResponseEntity<Object> getSubscription(@PathVariable Long id) {
        return ResponseEntity.ok(Optional.ofNullable(subscriptionService.getSubscriptionById(id)));
    }

    @PostMapping("subscription")
    public ResponseEntity<Object> addOrUpdateExistingSubscription(@RequestBody SubscriptionDto subscriptionDto) {
        return ResponseEntity.ok(subscriptionService.addSubscription(subscriptionDto));
    }

    @PatchMapping(value = "subscription/{id}")
    public ResponseEntity<?> updateSubscription(@RequestBody Map<String, Object> updates, @PathVariable("id") Long id) {
        return ResponseEntity.ok(subscriptionService.update(updates, id));
    }

    @PutMapping("subscription/attach_quotation")
    public ResponseEntity<?> attachQuotationToSubscription(@RequestBody AttachRequestDto attachRequestDto) {
        SubscriptionDto subscriptionDto = subscriptionService.getSubscriptionById(attachRequestDto.getParentId());

        if (subscriptionDto.getQuotation() != null) {
            return ResponseEntity.accepted().body(
                    Map.of("error", "Cannot be attached.",
                            "message", "Subscription with id "
                                    + attachRequestDto.getParentId() + " already owns quotation with id " + attachRequestDto.getChildId()));
        }

        return ResponseEntity.ok(subscriptionService.attachQuotation(attachRequestDto.getParentId(),
                quotationService.getQuotationById(attachRequestDto.getChildId())));
    }
}
