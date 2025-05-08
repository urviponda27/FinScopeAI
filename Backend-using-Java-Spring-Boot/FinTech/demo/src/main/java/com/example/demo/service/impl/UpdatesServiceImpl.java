package com.example.demo.service.impl;

import com.example.demo.dto.UpdatesDTO;
import com.example.demo.model.Updates;
import com.example.demo.model.User;
import com.example.demo.dao.UpdatesRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.service.UpdatesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UpdatesServiceImpl implements UpdatesService {

    @Autowired
    private UpdatesRepository updatesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void saveUpdates(Long userId, UpdatesDTO updatesDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            String json = updatesDTO.getSuggestions() == null
                    ? "{}"
                    : objectMapper.writeValueAsString(updatesDTO.getSuggestions());

            Optional<Updates> existing = updatesRepository.findByUser(user);

            Updates updates;
            if (existing.isPresent()) {
                updates = existing.get();
                updates.setCreditTier(updatesDTO.getCreditTier());
                updates.setSuggestionsJson(json);
            } else {
                updates = new Updates(null, updatesDTO.getCreditTier(), json, user);
                user.setUpdates(updates);
            }

            updatesRepository.save(updates);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert suggestions to JSON", e);
        }
    }


    @Override
    public UpdatesDTO getUpdatesByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Updates updates = updatesRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("No updates found for user"));

        try {
            Map<String, Integer> suggestions = objectMapper.readValue(
                    updates.getSuggestionsJson(), new TypeReference<Map<String, Integer>>() {}
            );
            return new UpdatesDTO(updates.getCreditTier(), suggestions);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON string", e);
        }
    }
}
