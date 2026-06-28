package com.example.purchase.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LotUpdateRequest(
        @NotBlank @Size(max = 255) String lotName,
        @NotBlank @Size(max = 50) String customerCode,
        BigDecimal price,
        @Pattern(regexp = "^(RUB|USD|EUR)$", message = "Валюта должна быть RUB, USD или EUR") String currencyCode,
        @Pattern(regexp = "^(Без НДС|18%|20%)$", message = "НДС должен быть Без НДС, 18% или 20%") String ndsRate,
        @Size(max = 500) String placeDelivery,
        LocalDateTime dateDelivery
) {}