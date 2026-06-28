package com.example.purchase.repository;

import jooqdata.tables.records.CustomerRecord;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    List<CustomerRecord> findAll();

    List<CustomerRecord> findAllFiltered(String nameLike,
                                         Boolean isOrganization,
                                         String sortBy,
                                         String sortDirection);

    Optional<CustomerRecord> findByCode(String code);

    CustomerRecord save(CustomerRecord record);

    void deleteByCode(String code);

    boolean existsByCode(String code);
}