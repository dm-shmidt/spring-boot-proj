package com.test.quotation.service;

import com.test.quotation.mapper.BaseMapper;
import com.test.quotation.model.dto.CustomerDto;
import com.test.quotation.model.entity.BaseEntity;
import com.test.quotation.model.entity.Customer;
import com.test.quotation.repository.base.CustomJpaRepository;
import com.test.quotation.util.EntityPatcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends BaseService<Customer, CustomerDto> {
    public CustomerService(CustomJpaRepository<Customer, Long> repository, BaseMapper<Customer, CustomerDto> mapper, @Qualifier("customerPatcher") EntityPatcher<Customer> patcher) {
        super(repository, mapper, patcher);
    }

    @Override
    public CustomerDto attachChild(Long id, BaseEntity childEntity) {
        return null;
    }
}
