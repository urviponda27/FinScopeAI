package com.example.demo.service.impl;

import com.example.demo.dto.FilteredSummaryDataDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.SummaryData;
import com.example.demo.model.User;
import com.example.demo.dao.SummaryDataRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.service.SummaryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SummaryDataServiceImpl implements SummaryDataService {

    private final SummaryDataRepository summaryDataRepository;
    private final UserRepository userRepository;

    @Autowired
    public SummaryDataServiceImpl(SummaryDataRepository summaryDataRepository, UserRepository userRepository) {
        this.summaryDataRepository = summaryDataRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public SummaryData saveSummaryData(SummaryData data) {
        Optional<SummaryData> existingDataOpt = summaryDataRepository.findByUserId(data.getUser().getId());

        if (existingDataOpt.isPresent()) {
            SummaryData existingData = existingDataOpt.get();

            // Update existing values
            existingData.setEntertainment(data.getEntertainment());
            existingData.setFood(data.getFood());
            existingData.setGambling(data.getGambling());
            existingData.setIncome(data.getIncome());
            existingData.setOthers(data.getOthers());
            existingData.setShopping(data.getShopping());
            existingData.setTravel(data.getTravel());
            existingData.setUtilities(data.getUtilities());
            existingData.setSavings(data.getSavings());
            existingData.setLoantype(data.getLoantype());
            existingData.setTotalActiveEMIs(data.getTotalActiveEMIs());
            existingData.setTotalMonthlyEMIAmount(data.getTotalMonthlyEMIAmount());
            existingData.setRegularEMIPayments(data.getRegularEMIPayments());
            existingData.setLateEMIPayments(data.getLateEMIPayments());
            existingData.setOverallEMIBehavior(data.getOverallEMIBehavior());
            existingData.setCurrCreditScore(data.getCurrCreditScore());

            return summaryDataRepository.save(existingData);
        }

        // If not present, just save as new
        return summaryDataRepository.save(data);
    }


    @Override
    @Transactional(readOnly = true)
    public List<SummaryData> getAllSummaryData() {
        try {
            return summaryDataRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all summary data: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public SummaryData getSummaryDataById(Long id) {
        try {
            return summaryDataRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Summary Data not found for ID: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Error fetching summary data by ID: " + id, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public FilteredSummaryDataDTO getSummaryByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for ID: " + userId));

        SummaryData summaryData = summaryDataRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Summary data not found for user ID: " + userId));

        return new FilteredSummaryDataDTO(
                summaryData.getEntertainment(),
                summaryData.getFood(),
                summaryData.getGambling(),
                summaryData.getIncome(),
                summaryData.getOthers(),
                summaryData.getShopping(),
                summaryData.getTravel(),
                summaryData.getUtilities(),
                summaryData.getSavings(),
                summaryData.getLoantype(),
                summaryData.getTotalActiveEMIs(),
                summaryData.getTotalMonthlyEMIAmount(),
                summaryData.getRegularEMIPayments(),
                summaryData.getLateEMIPayments(),
                summaryData.getOverallEMIBehavior(),
                summaryData.getCurrCreditScore()
        );
    }

}
