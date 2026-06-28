package com.example.purchase.dto.request;

import java.math.BigDecimal;

public record LotFilterRequest(
        String nameLike,
        String customerCode,
        String currencyCode,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        String sortBy,
        String sortDirection
) {}