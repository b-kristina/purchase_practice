package com.example.purchase.service.impl;

import com.example.purchase.dto.request.CustomerCreateRequest;
import com.example.purchase.dto.request.CustomerFilterRequest;
import com.example.purchase.dto.request.CustomerUpdateRequest;
import com.example.purchase.dto.response.CustomerResponse;
import com.example.purchase.exception.NotFoundException;
import com.example.purchase.mapper.CustomerMapper;
import com.example.purchase.repository.CustomerRepository;
import com.example.purchase.service.CustomerService;
import jooqdata.tables.records.CustomerRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerServiceImpl(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAll(CustomerFilterRequest filter) {
        List<CustomerRecord> records = repository.findAllFiltered(
                filter.nameLike(),
                filter.isOrganization(),
                filter.sortBy(),
                filter.sortDirection()
        );
        return mapper.toResponseList(records);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getByCode(String code) {
        CustomerRecord record = repository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Контрагент с кодом " + code + " не найден"));
        return mapper.toResponse(record);
    }

    @Override
    public CustomerResponse create(CustomerCreateRequest request) {
        if (repository.existsByCode(request.customerCode())) {
            throw new IllegalArgumentException("Контрагент с кодом " + request.customerCode() + " уже существует");
        }

        CustomerRecord record = mapper.toRecord(request);
        CustomerRecord saved = repository.save(record);
        return mapper.toResponse(saved);
    }

    @Override
    public CustomerResponse update(String code, CustomerUpdateRequest request) {
        CustomerRecord record = repository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Контрагент с кодом " + code + " не найден"));

        mapper.updateRecordFromRequest(request, record);
        CustomerRecord updated = repository.save(record);
        return mapper.toResponse(updated);
    }

    @Override
    public void delete(String code) {
        if (!repository.existsByCode(code)) {
            throw new NotFoundException("Контрагент с кодом " + code + " не найден");
        }
        repository.deleteByCode(code);
    }
}