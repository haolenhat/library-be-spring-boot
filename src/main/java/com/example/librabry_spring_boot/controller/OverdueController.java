package com.example.librabry_spring_boot.controller;

import com.example.librabry_spring_boot.dto.FineInfoDTO;
import com.example.librabry_spring_boot.model.OverdueRecord;
import com.example.librabry_spring_boot.service.OverdueRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/overdue")
public class OverdueController {

    @Autowired
    private OverdueRecordService overdueRecordService;

    @PostMapping("/update")
    public ResponseEntity<String> updateOverdueRecords() {
        overdueRecordService.updateOverdueRecords();
        return ResponseEntity.ok("Đã cập nhật các bản ghi quá hạn.");
    }


    @GetMapping("/all")
    public ResponseEntity<List<FineInfoDTO>> getFines() {
        List<FineInfoDTO> fines = overdueRecordService.getAllFines();
        return ResponseEntity.ok(fines);
    }
}
