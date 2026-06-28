package com.example.purchase.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LotResponse(
        Integer lotId,
        String lotName,
        String customerCode,
        BigDecimal price,
        String currencyCode,
        String ndsRate,
        String placeDelivery,
        LocalDateTime dateDelivery
) {}