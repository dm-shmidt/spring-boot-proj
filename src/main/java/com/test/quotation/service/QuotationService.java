package com.test.quotation.service;

import com.test.quotation.exception.NotFoundException;
import com.test.quotation.mapper.QuotationMapper;
import com.test.quotation.model.dto.QuotationDto;
import com.test.quotation.model.entity.Quotation;
import com.test.quotation.repository.QuotationRepository;
import com.test.quotation.util.PropsMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuotationService {
    private final QuotationRepository quotationRepository;

    private final QuotationMapper mapper = QuotationMapper.INSTANCE;

    private final PropsMapper<Quotation> propsMapper = new PropsMapper<>();

    public QuotationService(QuotationRepository quotationRepository) {
        this.quotationRepository = quotationRepository;
    }

    public List<QuotationDto> getAllQuotations() {
        return mapper.toQuotationDtoList(quotationRepository.findAll());
    }

    public QuotationDto getQuotationById(Long id) {
        return mapper.toDto(quotationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quotation wiht id " + id + " not found.")));
    }

    public QuotationDto addQuotation(QuotationDto quotationDto) {
        return mapper.toDto(quotationRepository.save(mapper.toEntity(quotationDto)));
    }

    public QuotationDto update(Map<String, Object> updates, Long id) {
        Quotation quotationFromDB = quotationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quotation with id " + id + " not found."));

        propsMapper.updateValues(updates, quotationFromDB);

        return mapper.toDto(quotationRepository.save(quotationFromDB));

    }
}
