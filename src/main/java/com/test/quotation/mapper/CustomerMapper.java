package com.test.quotation.mapper;

import com.test.quotation.model.dto.CustomerDto;
import com.test.quotation.model.entity.Customer;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper()
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer mapToCustomer(CustomerDto customerDto);

    @Named("toCustomerDto")
    CustomerDto mapToCustomerDto(Customer customer);

    @IterableMapping(qualifiedByName = "toCustomerDto")
    List<CustomerDto> toCustomerDtoList(List<Customer> customerList);

}
