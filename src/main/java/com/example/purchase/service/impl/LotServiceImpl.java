package com.example.purchase.service.impl;

import com.example.purchase.dto.request.LotCreateRequest;
import com.example.purchase.dto.request.LotFilterRequest;
import com.example.purchase.dto.request.LotUpdateRequest;
import com.example.purchase.dto.response.LotResponse;
import com.example.purchase.exception.NotFoundException;
import com.example.purchase.mapper.LotMapper;
import com.example.purchase.repository.CustomerRepository;
import com.example.purchase.repository.LotRepository;
import com.example.purchase.service.LotService;
import jooqdata.tables.records.LotRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;
    private final CustomerRepository customerRepository;
    private final LotMapper mapper;

    public LotServiceImpl(LotRepository lotRepository,
                          CustomerRepository customerRepository,
                          LotMapper mapper) {
        this.lotRepository = lotRepository;
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LotResponse> getAll(LotFilterRequest filter) {
        List<LotRecord> records = lotRepository.findAllFiltered(
                filter.nameLike(),
                filter.customerCode(),
                filter.currencyCode(),
                filter.minPrice(),
                filter.maxPrice(),
                filter.sortBy(),
                filter.sortDirection()
        );
        return mapper.toResponseList(records);
    }

    @Override
    @Transactional(readOnly = true)
    public LotResponse getById(Integer id) {
        LotRecord record = lotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Лот с ID " + id + " не найден"));
        return mapper.toResponse(record);
    }

    @Override
    public LotResponse create(LotCreateRequest request) {
        if (!customerRepository.existsByCode(request.customerCode())) {
            throw new IllegalArgumentException("Контрагент с кодом " + request.customerCode() + " не найден");
        }

        LotRecord record = mapper.toRecord(request);
        LotRecord saved = lotRepository.save(record);
        return mapper.toResponse(saved);
    }

    @Override
    public LotResponse update(Integer id, LotUpdateRequest request) {
        LotRecord record = lotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Лот с ID " + id + " не найден"));

        if (!customerRepository.existsByCode(request.customerCode())) {
            throw new IllegalArgumentException("Контрагент с кодом " + request.customerCode() + " не найден");
        }

        mapper.updateRecordFromRequest(request, record);
        LotRecord updated = lotRepository.save(record);
        return mapper.toResponse(updated);
    }

    @Override
    public void delete(Integer id) {
        if (lotRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Лот с ID " + id + " не найден");
        }
        lotRepository.deleteById(id);
    }
}