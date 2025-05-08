package com.example.demo.service;


import com.example.demo.dto.FilteredSummaryDataDTO;
import com.example.demo.model.SummaryData;
import java.util.List;

public interface SummaryDataService {

    SummaryData saveSummaryData(SummaryData data);

    List<SummaryData> getAllSummaryData();

    SummaryData getSummaryDataById(Long id);

    FilteredSummaryDataDTO getSummaryByUserId(Long userId);
}
