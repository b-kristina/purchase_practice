package com.example.purchase.mapper;

import com.example.purchase.dto.request.CustomerCreateRequest;
import com.example.purchase.dto.request.CustomerUpdateRequest;
import com.example.purchase.dto.response.CustomerResponse;
import jooqdata.tables.records.CustomerRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponse toResponse(CustomerRecord record);

    List<CustomerResponse> toResponseList(List<CustomerRecord> records);

    CustomerRecord toRecord(CustomerCreateRequest request);

    void updateRecordFromRequest(CustomerUpdateRequest request, @MappingTarget CustomerRecord record);
}