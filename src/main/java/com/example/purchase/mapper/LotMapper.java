package com.example.purchase.mapper;

import com.example.purchase.dto.request.LotCreateRequest;
import com.example.purchase.dto.request.LotUpdateRequest;
import com.example.purchase.dto.response.LotResponse;
import jooqdata.tables.records.LotRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LotMapper {

    LotResponse toResponse(LotRecord record);

    List<LotResponse> toResponseList(List<LotRecord> records);

    LotRecord toRecord(LotCreateRequest request);

    void updateRecordFromRequest(LotUpdateRequest request, @MappingTarget LotRecord record);
}