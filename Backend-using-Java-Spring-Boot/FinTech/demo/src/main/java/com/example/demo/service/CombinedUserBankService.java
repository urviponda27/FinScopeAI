package com.example.demo.service;

import com.example.demo.dto.CombinedUserBankDTO;
import com.example.demo.model.User;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface CombinedUserBankService {
    ResponseEntity<Resource> getPdfAndConsentByUserId(Long userId);
    boolean doesUserExist(Long userId);
    User createUserWithBankStatement(CombinedUserBankDTO dto);


}
