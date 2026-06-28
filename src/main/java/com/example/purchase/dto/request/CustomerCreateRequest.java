package com.example.purchase.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerCreateRequest(
        @NotBlank @Size(max = 50) String customerCode,
        @NotBlank @Size(max = 255) String customerName,
        @Pattern(regexp = "^\\d{10,12}$", message = "ИНН должен содержать 10 или 12 цифр") String customerInn,
        @Size(max = 20) String customerKpp,
        @Size(max = 500) String customerLegalAddress,
        @Size(max = 500) String customerPostalAddress,
        @Size(max = 100) String customerEmail,
        @Size(max = 50) String customerCodeMain,
        Boolean isOrganization,
        Boolean isPerson
) {}