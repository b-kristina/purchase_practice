package com.example.purchase.repository.impl;

import com.example.purchase.repository.CustomerRepository;
import jooqdata.tables.records.CustomerRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jooqdata.tables.Customer.CUSTOMER;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final DSLContext dsl;

    public CustomerRepositoryImpl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<CustomerRecord> findAll() {
        return dsl.selectFrom(CUSTOMER).fetch();
    }

    @Override
    public List<CustomerRecord> findAllFiltered(String nameLike,
                                                Boolean isOrganization,
                                                String sortBy,
                                                String sortDirection) {
        List<Condition> conditions = buildFilterConditions(nameLike, isOrganization);

        List<SortField<?>> sorts = buildSortFields(sortBy, sortDirection);

        return dsl.selectFrom(CUSTOMER)
                .where(conditions)
                .orderBy(sorts)
                .fetch();
    }

    @Override
    public Optional<CustomerRecord> findByCode(String code) {
        return dsl.selectFrom(CUSTOMER)
                .where(CUSTOMER.CUSTOMER_CODE.eq(code))
                .fetchOptional();
    }

    @Override
    public CustomerRecord save(CustomerRecord record) {
        return dsl.insertInto(CUSTOMER)
                .set(record)
                .onConflict(CUSTOMER.CUSTOMER_CODE)
                .doUpdate()
                .set(record)
                .returning()
                .fetchOne();
    }

    @Override
    public void deleteByCode(String code) {
        dsl.deleteFrom(CUSTOMER)
                .where(CUSTOMER.CUSTOMER_CODE.eq(code))
                .execute();
    }

    @Override
    public boolean existsByCode(String code) {
        return dsl.fetchExists(
                dsl.selectOne().from(CUSTOMER).where(CUSTOMER.CUSTOMER_CODE.eq(code))
        );
    }


    private List<Condition> buildFilterConditions(String nameLike, Boolean isOrganization) {
        List<Condition> conditions = new ArrayList<>();

        if (nameLike != null && !nameLike.isBlank()) {
            conditions.add(CUSTOMER.CUSTOMER_NAME.likeIgnoreCase("%" + nameLike + "%"));
        }
        if (isOrganization != null) {
            conditions.add(CUSTOMER.IS_ORGANIZATION.eq(isOrganization));
        }

        return conditions;
    }

    private List<SortField<?>> buildSortFields(String sortBy, String sortDirection) {
        List<SortField<?>> sorts = new ArrayList<>();

        if (sortBy != null && !sortBy.isBlank()) {
            Field<?> field = getSortField(sortBy);
            SortOrder order = "DESC".equalsIgnoreCase(sortDirection) ? SortOrder.DESC : SortOrder.ASC;
            sorts.add(field.sort(order));
        }

        return sorts;
    }

    private Field<?> getSortField(String sortBy) {
        return switch (sortBy.toLowerCase()) {
            case "name" -> CUSTOMER.CUSTOMER_NAME;
            case "inn" -> CUSTOMER.CUSTOMER_INN;
            case "email" -> CUSTOMER.CUSTOMER_EMAIL;
            default -> CUSTOMER.CUSTOMER_CODE;
        };
    }
}