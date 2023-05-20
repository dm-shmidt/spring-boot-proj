package com.test.quotation.service;

import com.test.quotation.mapper.QuotationMapper;
import com.test.quotation.model.dto.QuotationDto;
import com.test.quotation.repository.QuotationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class QuotationService {
    private final QuotationRepository quotationRepository;

    private final QuotationMapper mapper = QuotationMapper.INSTANCE;

    public QuotationService(QuotationRepository quotationRepository) {
        this.quotationRepository = quotationRepository;
    }

    public List<QuotationDto> getAllQuotations() {
        return mapper.toQuotationDtoList(quotationRepository.findAll());
    }

    public QuotationDto getQuotationById(Long id) {
        return mapper.toDto(quotationRepository.findById(id)
                .orElseThrow(NoSuchElementException::new));
    }
}
