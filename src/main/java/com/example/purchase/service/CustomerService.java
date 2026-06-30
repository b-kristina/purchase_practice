package com.example.purchase.service;

import com.example.purchase.dto.request.CustomerCreateRequest;
import com.example.purchase.dto.request.CustomerFilterRequest;
import com.example.purchase.dto.request.CustomerUpdateRequest;
import com.example.purchase.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {

    List<CustomerResponse> getAll(CustomerFilterRequest filter);

    CustomerResponse getByCode(String code);

    CustomerResponse create(CustomerCreateRequest request);

    CustomerResponse update(String code, CustomerUpdateRequest request);

    void delete(String code);
}