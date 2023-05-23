package com.test.quotation.repository;

import com.test.quotation.model.entity.Quotation;
import com.test.quotation.repository.base.CustomJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotationRepository extends CustomJpaRepository<Quotation, Long> {
    List<Quotation> findAllByCustomerId(Long customerId);
}
