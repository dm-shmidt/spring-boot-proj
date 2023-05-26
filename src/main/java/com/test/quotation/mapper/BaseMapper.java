package com.test.quotation.mapper;

import com.test.quotation.model.dto.BaseRecord;
import com.test.quotation.model.entity.BaseEntity;

import java.util.List;

public interface BaseMapper<E extends BaseEntity, D extends BaseRecord> {
    D toDto(E entity);

    E toEntity(D dto);

    List<D> toDtoList(List<E> entityList);

}
