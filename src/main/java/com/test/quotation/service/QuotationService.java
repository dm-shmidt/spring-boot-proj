package com.test.quotation.service;

import com.test.quotation.exception.AttachmentFailedException;
import com.test.quotation.exception.NotFoundException;
import com.test.quotation.mapper.BaseMapper;
import com.test.quotation.model.dto.QuotationDto;
import com.test.quotation.model.entity.BaseEntity;
import com.test.quotation.model.entity.Customer;
import com.test.quotation.model.entity.Quotation;
import com.test.quotation.repository.base.CustomJpaRepository;
import com.test.quotation.util.EntityPatcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class QuotationService extends BaseService<Quotation, QuotationDto> {

    public QuotationService(CustomJpaRepository<Quotation, Long> repository, BaseMapper<Quotation, QuotationDto> mapper, @Qualifier("quotationPatcher") EntityPatcher<Quotation> patcher) {
        super(repository, mapper, patcher);
    }

    @Override
    public QuotationDto attachChild(Long id, BaseEntity childEntity) {
        if (!(childEntity instanceof Customer)) {
            throw new AttachmentFailedException("Child entity is not of Customer type.");
        }
        Quotation quotation = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quotation with id " + id + " not found."));
        quotation.setCustomer((Customer) childEntity);
        repository.saveAndFlush(quotation);
        repository.refresh(quotation);
        return mapper.toDto(quotation);
    }
}
