package com.example.purchase.repository.impl;

import com.example.purchase.repository.LotRepository;
import jooqdata.tables.records.LotRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jooqdata.tables.Lot.LOT;

@Repository
public class LotRepositoryImpl implements LotRepository {

    private final DSLContext dsl;

    public LotRepositoryImpl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<LotRecord> findAll() {
        return dsl.selectFrom(LOT).fetch();
    }

    @Override
    public List<LotRecord> findAllFiltered(String nameLike,
                                           String customerCode,
                                           String currencyCode,
                                           BigDecimal minPrice,
                                           BigDecimal maxPrice,
                                           String sortBy,
                                           String sortDirection) {
        List<Condition> conditions = buildFilterConditions(
                nameLike, customerCode, currencyCode, minPrice, maxPrice
        );

        List<SortField<?>> sorts = buildSortFields(sortBy, sortDirection);

        return dsl.selectFrom(LOT)
                .where(conditions)
                .orderBy(sorts)
                .fetch();
    }

    @Override
    public Optional<LotRecord> findById(Integer id) {
        return dsl.selectFrom(LOT)
                .where(LOT.LOT_ID.eq(id))
                .fetchOptional();
    }

    @Override
    public LotRecord save(LotRecord record) {
        if (record.getLotId() == null) {
            return dsl.insertInto(LOT)
                    .set(record)
                    .returning()
                    .fetchOne();
        } else {
            return dsl.update(LOT)
                    .set(record)
                    .where(LOT.LOT_ID.eq(record.getLotId()))
                    .returning()
                    .fetchOne();
        }
    }

    @Override
    public void deleteById(Integer id) {
        dsl.deleteFrom(LOT)
                .where(LOT.LOT_ID.eq(id))
                .execute();
    }


    private List<Condition> buildFilterConditions(String nameLike,
                                                  String customerCode,
                                                  String currencyCode,
                                                  BigDecimal minPrice,
                                                  BigDecimal maxPrice) {
        List<Condition> conditions = new ArrayList<>();

        if (nameLike != null && !nameLike.isBlank()) {
            conditions.add(LOT.LOT_NAME.likeIgnoreCase("%" + nameLike + "%"));
        }
        if (customerCode != null && !customerCode.isBlank()) {
            conditions.add(LOT.CUSTOMER_CODE.eq(customerCode));
        }
        if (currencyCode != null && !currencyCode.isBlank()) {
            conditions.add(LOT.CURRENCY_CODE.eq(currencyCode));
        }
        if (minPrice != null) {
            conditions.add(LOT.PRICE.ge(minPrice));
        }
        if (maxPrice != null) {
            conditions.add(LOT.PRICE.le(maxPrice));
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
            case "name" -> LOT.LOT_NAME;
            case "price" -> LOT.PRICE;
            case "currency" -> LOT.CURRENCY_CODE;
            case "date" -> LOT.DATE_DELIVERY;
            default -> LOT.LOT_ID;
        };
    }
}