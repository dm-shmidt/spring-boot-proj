package com.test.quotation.controller;

import com.test.quotation.model.dto.CustomerDto;
import com.test.quotation.model.dto.QuotationDto;
import com.test.quotation.model.dto.SubscriptionDto;
import com.test.quotation.model.entity.Customer;
import com.test.quotation.model.entity.Quotation;
import com.test.quotation.model.entity.Subscription;
import com.test.quotation.model.request.AttachRequest;
import com.test.quotation.model.request.IdRequest;
import com.test.quotation.service.BaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@Transactional
public class AppController {

    private final BaseService<Customer, CustomerDto> customerService;
    private final BaseService<Quotation, QuotationDto> quotationService;
    private final BaseService<Subscription, SubscriptionDto> subscriptionService;
//    private final SubscriptionService1 subscriptionService1;

    public AppController(@Qualifier("customerService") BaseService<Customer, CustomerDto> customerService,
                         @Qualifier("quotationService") BaseService<Quotation, QuotationDto> quotationService,
                         @Qualifier("subscriptionService") BaseService<Subscription, SubscriptionDto> subscriptionService) {
        this.customerService = customerService;
        this.quotationService = quotationService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("customer")
    public ResponseEntity<?> getAllCustomers() {
        return ResponseEntity.ok(Optional.ofNullable(customerService.getAll()));
    }

    @GetMapping("customer/id")
    public ResponseEntity<?> getCustomerById(@RequestBody @Valid IdRequest request) {
        return ResponseEntity.ok(customerService.getDtoById(request.id()));
    }

    @PostMapping("customer")
    public ResponseEntity<?> addCustomer(@RequestBody @Valid CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.add(customerDto));
    }

    @PatchMapping(value = "customer")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDto customerDtoUpdates) {
        return ResponseEntity.ok(customerService.update(customerDtoUpdates));
    }

    @GetMapping("quotation")
    public ResponseEntity<Object> getAllQuotations() {
        return ResponseEntity.ok(Optional.ofNullable(quotationService.getAll()));
    }

    @GetMapping("quotation/id")
    public ResponseEntity<Object> getQuotationById(@RequestBody IdRequest request) {
        return ResponseEntity.ok(Optional.ofNullable(quotationService.getDtoById(request.id())));
    }

    @PostMapping("quotation")
    public ResponseEntity<Object> addQuotation(@RequestBody QuotationDto quotationDto) {
        QuotationDto newQuotationDto = quotationService.add(quotationDto);
        return ResponseEntity.ok(newQuotationDto);
    }

    @PatchMapping(value = "quotation")
    public ResponseEntity<?> updateQuotation(@RequestBody QuotationDto updates) {
        return ResponseEntity.ok(quotationService.update(updates));
    }

    @PutMapping("quotation/attach_customer")
    public ResponseEntity<?> attachCustomerToQuotation(@RequestBody AttachRequest attachRequestDto) {
        QuotationDto quotationDto = quotationService.getDtoById(attachRequestDto.getParentId());

        if (quotationDto.customer() != null) {
            return ResponseEntity.accepted().body(
                    Map.of("error", "Cannot be attached.",
                            "message", "Quotation with id "
                            + attachRequestDto.getParentId() + " already owns customer with id " + attachRequestDto.getChildId()));
        }

        return ResponseEntity.ok(quotationService.attachChild(attachRequestDto.getParentId(),
                customerService.getEntityById(attachRequestDto.getChildId())));
    }

    @GetMapping("subscription/id")
    public ResponseEntity<Object> getSubscription(@RequestBody IdRequest request) {
        return ResponseEntity.ok(Optional.ofNullable(subscriptionService.getDtoById(request.id())));
    }

    @PostMapping("subscription")
    public ResponseEntity<Object> addSubscription(@RequestBody SubscriptionDto subscriptionDto) {
        return ResponseEntity.ok(subscriptionService.add(subscriptionDto));
    }

    @PatchMapping(value = "subscription")
    public ResponseEntity<?> updateSubscription(@RequestBody SubscriptionDto updates) {
        return ResponseEntity.ok(subscriptionService.update(updates));
    }

    @PutMapping("subscription/attach_quotation")
    public ResponseEntity<?> attachQuotationToSubscription(@RequestBody AttachRequest attachRequest) {
        SubscriptionDto subscriptionDto = subscriptionService.getDtoById(attachRequest.getParentId());

        if (subscriptionDto.quotation() != null) {
            return ResponseEntity.accepted().body(
                    Map.of("error", "Cannot be attached.",
                            "message", "Subscription with id "
                                    + attachRequest.getParentId() + " already owns quotation with id " + attachRequest.getChildId()));
        }

        return ResponseEntity.ok(subscriptionService.attachChild(attachRequest.getParentId(),
                quotationService.getEntityById(attachRequest.getChildId())));
    }
}
