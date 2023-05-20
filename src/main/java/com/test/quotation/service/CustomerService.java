package com.test.quotation.service;

import com.test.quotation.mapper.CustomerMapper;
import com.test.quotation.model.dto.CustomerDto;
import com.test.quotation.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper = CustomerMapper.INSTANCE;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDto> getAllCustomers() {
        return mapper.toDtoList(customerRepository.findAll());
    }

    public CustomerDto addCustomer(CustomerDto customerDto) {
        return mapper.toDto(customerRepository.save(mapper.toEntity(customerDto)));
    }
}
