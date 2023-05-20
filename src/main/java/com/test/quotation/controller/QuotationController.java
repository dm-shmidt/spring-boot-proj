package com.test.quotation.controller;

import com.test.quotation.model.dto.CustomerDto;
import com.test.quotation.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class QuotationController {

    private CustomerService customerService;

    public QuotationController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("customer")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.of(Optional.ofNullable(customerService.getAllCustomers()));
    }
}
