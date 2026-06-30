package com.example.purchase.controller;

import com.example.purchase.dto.request.LotCreateRequest;
import com.example.purchase.dto.request.LotFilterRequest;
import com.example.purchase.dto.request.LotUpdateRequest;
import com.example.purchase.dto.response.LotResponse;
import com.example.purchase.service.LotService;
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
@RequestMapping("/api/lots")
public class LotController {

    private final LotService lotService;

    public LotController(LotService lotService) {
        this.lotService = lotService;
    }

    @GetMapping
    public List<LotResponse> getAll(LotFilterRequest filter) {
        return lotService.getAll(filter);
    }

    @GetMapping("/{id}")
    public LotResponse getById(@PathVariable Integer id) {
        return lotService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LotResponse create(@Valid @RequestBody LotCreateRequest request) {
        return lotService.create(request);
    }

    @PutMapping("/{id}")
    public LotResponse update(@PathVariable Integer id,
                              @Valid @RequestBody LotUpdateRequest request) {
        return lotService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        lotService.delete(id);
    }
}