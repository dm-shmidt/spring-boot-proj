package com.test.quotation.service;

import com.test.quotation.exception.NotFoundException;
import com.test.quotation.mapper.QuotationMapper;
import com.test.quotation.model.dto.QuotationDto;
import com.test.quotation.model.entity.Customer;
import com.test.quotation.model.entity.Quotation;
import com.test.quotation.repository.QuotationRepository;
import com.test.quotation.util.EntityPatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuotationService {
    private final QuotationRepository quotationRepository;

    private final QuotationMapper mapper = QuotationMapper.INSTANCE;

    //    private final Patcher patcher;
    private final EntityPatcher<Quotation> entityPatcher = new EntityPatcher<>();

    public QuotationService(QuotationRepository quotationRepository) {
        this.quotationRepository = quotationRepository;
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

        quotationFromDB = entityPatcher.patch(updates, quotationFromDB);

        return mapper.toDto(quotationRepository.save(quotationFromDB));
    }

    public QuotationDto attachCustomer(Long quotationId, Customer customer) {

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
