package com.test.quotation.repository;

import com.test.quotation.model.entity.Customer;
import com.test.quotation.repository.base.CustomJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CustomJpaRepository<Customer, Long> {
}
