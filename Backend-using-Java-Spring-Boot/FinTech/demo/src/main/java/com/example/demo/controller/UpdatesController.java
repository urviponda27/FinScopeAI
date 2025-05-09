package com.example.demo.controller;

import com.example.demo.dto.UpdatesDTO;
import com.example.demo.service.UpdatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/updates")
@CrossOrigin(origins = "*")
public class UpdatesController {

    @Autowired
    private UpdatesService updatesService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<String> saveUpdates(@PathVariable Long userId, @RequestBody UpdatesDTO updatesDTO) {
        updatesService.saveUpdates(userId, updatesDTO);
        return ResponseEntity.ok("Updates saved successfully");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UpdatesDTO> getUpdates(@PathVariable Long userId) {
        UpdatesDTO dto = updatesService.getUpdatesByUserId(userId);
        return ResponseEntity.ok(dto);
    }
}
