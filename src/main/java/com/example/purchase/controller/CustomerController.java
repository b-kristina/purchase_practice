package com.example.purchase.controller;

import com.example.purchase.dto.request.CustomerCreateRequest;
import com.example.purchase.dto.request.CustomerFilterRequest;
import com.example.purchase.dto.request.CustomerUpdateRequest;
import com.example.purchase.dto.response.CustomerResponse;
import com.example.purchase.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerResponse> getAll(CustomerFilterRequest filter) {
        return customerService.getAll(filter);
    }

    @GetMapping("/{code}")
    public CustomerResponse getByCode(@PathVariable String code) {
        return customerService.getByCode(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse create(@Valid @RequestBody CustomerCreateRequest request) {
        return customerService.create(request);
    }

    @PutMapping("/{code}")
    public CustomerResponse update(@PathVariable String code,
                                   @Valid @RequestBody CustomerUpdateRequest request) {
        return customerService.update(code, request);
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String code) {
        customerService.delete(code);
    }
}