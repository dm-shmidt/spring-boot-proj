package com.test.quotation.service;

import com.test.quotation.exception.NotFoundException;
import com.test.quotation.mapper.CustomerMapper;
import com.test.quotation.model.dto.CustomerDto;
import com.test.quotation.model.entity.Customer;
import com.test.quotation.repository.CustomerRepository;
import com.test.quotation.util.PropsMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper = CustomerMapper.INSTANCE;

    private final PropsMapper<Customer> propsMapper = new PropsMapper<>();

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDto> getAllCustomers() {
        return mapper.toDtoList(customerRepository.findAll());
    }

    public CustomerDto getCustomerDtoById(Long id) {
        return mapper.toDto(customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer with id " + id + " not found.")));
    }

    public CustomerDto addCustomer(CustomerDto customerDto) {
        return mapper.toDto(customerRepository.save(mapper.toEntity(customerDto)));
    }

    public CustomerDto update(Map<String, Object> updates, Long id) {
        Customer customerFromDB = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer with id " + id + " not found."));

        propsMapper.updateValues(updates, customerFromDB);

        return mapper.toDto(customerRepository.save(customerFromDB));
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Failed to find customer with id " + id + " not found."));
    }
}
