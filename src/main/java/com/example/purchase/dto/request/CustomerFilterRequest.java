package com.example.purchase.dto.request;

public record CustomerFilterRequest(
        String nameLike,
        Boolean isOrganization,
        String sortBy,
        String sortDirection
) {}