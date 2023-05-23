package com.test.quotation.service;

import com.test.quotation.exception.RecordWithFKAlreadyExistsException;
import com.test.quotation.model.entity.Customer;
import com.test.quotation.util.Patcher;
import com.test.quotation.exception.NotFoundException;
import com.test.quotation.exception.UpdateFailedException;
import com.test.quotation.mapper.QuotationMapper;
import com.test.quotation.model.dto.QuotationDto;
import com.test.quotation.model.entity.Quotation;
import com.test.quotation.repository.QuotationRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@Service
public class QuotationService {
    private final QuotationRepository quotationRepository;

    private final QuotationMapper mapper = QuotationMapper.INSTANCE;

    private final Patcher patcher;

    public QuotationService(QuotationRepository quotationRepository, Patcher patcher) {
        this.quotationRepository = quotationRepository;
        this.patcher = patcher;
    }

    public List<QuotationDto> getAllQuotations() {
        return mapper.toQuotationDtoList(quotationRepository.findAll());
    }

    public QuotationDto getQuotationDtoById(Long id) {
        return mapper.toDto(quotationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quotation with id " + id + " not found.")));
    }

    public QuotationDto addQuotation(QuotationDto quotationDto) {
        Quotation quotation = quotationRepository.saveAndFlush(mapper.toEntity(quotationDto));
        quotationRepository.refresh(quotation);
        return mapper.toDto(quotation);
    }

    public QuotationDto update(Map<String, Object> updates, Long id) {
        Quotation quotationFromDB = quotationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quotation with id " + id + " not found."));

        QuotationDto quotationDto = mapper.toDto(quotationFromDB);
        try {
            quotationDto = (QuotationDto) patcher.patch(updates, quotationDto);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new UpdateFailedException("Patch failed.");
        }

        quotationFromDB = mapper.toEntity(quotationDto);

        return mapper.toDto(quotationRepository.save(quotationFromDB));
    }

    public QuotationDto attachCustomer(Long quotationId, Customer customer) {

        if (!quotationRepository.findAllByCustomerId(customer.getId()).isEmpty()) {
            throw new RecordWithFKAlreadyExistsException(
                    "Quotation with customerId " + customer.getId() + " as a foreign key already exists.");
        }

        Quotation quotation = quotationRepository.findById(quotationId)
                .orElseThrow(() -> new NotFoundException("Quotation with id " + quotationId + " not found."));
        quotation.setCustomer(customer);
        quotationRepository.saveAndFlush(quotation);
        quotationRepository.refresh(quotation);
        return mapper.toDto(quotation);
    }

    public Quotation getQuotationById(Long id) {
        return quotationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Failed to find quotation with id " + id + "."));

    }
}
