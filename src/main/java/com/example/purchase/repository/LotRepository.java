package com.example.purchase.repository;

import jooqdata.tables.records.LotRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LotRepository {

    List<LotRecord> findAll();

    List<LotRecord> findAllFiltered(String nameLike,
                                    String customerCode,
                                    String currencyCode,
                                    BigDecimal minPrice,
                                    BigDecimal maxPrice,
                                    String sortBy,
                                    String sortDirection);

    Optional<LotRecord> findById(Integer id);

    LotRecord save(LotRecord record);

    void deleteById(Integer id);
}