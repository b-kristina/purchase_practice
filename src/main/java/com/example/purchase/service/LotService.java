package com.example.purchase.service;

import com.example.purchase.dto.request.LotCreateRequest;
import com.example.purchase.dto.request.LotFilterRequest;
import com.example.purchase.dto.request.LotUpdateRequest;
import com.example.purchase.dto.response.LotResponse;

import java.util.List;

public interface LotService {

    List<LotResponse> getAll(LotFilterRequest filter);

    LotResponse getById(Integer id);

    LotResponse create(LotCreateRequest request);

    LotResponse update(Integer id, LotUpdateRequest request);

    void delete(Integer id);
}