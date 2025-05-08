package com.example.demo.service;


import com.example.demo.dto.UpdatesDTO;

public interface UpdatesService {
    void saveUpdates(Long userId, UpdatesDTO updatesDTO);
    UpdatesDTO getUpdatesByUserId(Long userId);
}
