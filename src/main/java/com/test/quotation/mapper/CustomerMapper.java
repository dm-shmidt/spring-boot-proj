package com.test.quotation.mapper;

import com.test.quotation.model.dto.CustomerDto;
import com.test.quotation.model.entity.Customer;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface CustomerMapper extends BaseMapper<Customer, CustomerDto> {

    @Override
    Customer toEntity(CustomerDto customerDto);

    @Named("toCustomerDto")
    CustomerDto toDto(Customer customer);

    @IterableMapping(qualifiedByName = "toCustomerDto")
    List<CustomerDto> toDtoList(List<Customer> customerList);

}
