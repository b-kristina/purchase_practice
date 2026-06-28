package com.example.purchase.dto.response;

public record CustomerResponse(
        String customerCode,
        String customerName,
        String customerInn,
        String customerKpp,
        String customerLegalAddress,
        String customerPostalAddress,
        String customerEmail,
        String customerCodeMain,
        Boolean isOrganization,
        Boolean isPerson
) {}