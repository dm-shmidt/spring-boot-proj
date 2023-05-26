package com.test.quotation.service;

import com.test.quotation.exception.NotFoundException;
import com.test.quotation.exception.UpdateFailedException;
import com.test.quotation.mapper.BaseMapper;
import com.test.quotation.model.dto.BaseRecord;
import com.test.quotation.model.entity.BaseEntity;
import com.test.quotation.repository.base.CustomJpaRepository;
import com.test.quotation.util.EntityPatcher;

import java.util.List;

public abstract class BaseService<E extends BaseEntity, D extends BaseRecord> {
    private final CustomJpaRepository<E, Long> repository;
    private final BaseMapper<E, D> mapper;
    private final EntityPatcher<E> patcher;

    public BaseService(CustomJpaRepository<E, Long> repository, BaseMapper<E, D> mapper, EntityPatcher<E> patcher) {
        this.repository = repository;
        this.mapper = mapper;
        this.patcher = patcher;
    }

    
    public List<D> getAll() {
        return mapper.toDtoList(repository.findAll());
    }



    public D getDtoById(Long id) {
        return mapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("getDtoById() -> Entity with id " + id + " not found.")));
    }

    
    public D add(D dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    
    public D update(BaseRecord updates) {
        if (updates.id() == null) {
            throw new UpdateFailedException("ID should not be null");
        }
        E BaseEntityFromDB = repository.findById(updates.id())
                .orElseThrow(() -> new NotFoundException("update() -> Entity with id " + updates.id() + " not found."));

        BaseEntityFromDB = patcher.patch(updates, BaseEntityFromDB);

        return mapper.toDto(repository.save(BaseEntityFromDB));
    }
    
    public E getEntityById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("getEntityById() -> Failed to find entity with id " + id + "."));
    }

    public abstract D attachChild(Long id, BaseEntity childEntity);
}
