package com.example.librabry_spring_boot.controller;


import com.example.librabry_spring_boot.dto.LoanRecordRequestDTO;
import com.example.librabry_spring_boot.dto.LoanRecordResponseDTO;
import com.example.librabry_spring_boot.service.LoanRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/loan-records")
public class LoanRecordController {

    @Autowired
    private LoanRecordService loanRecordService;

    @GetMapping("/all")
    public List<LoanRecordResponseDTO> getAllLoanRecords() {
        return loanRecordService.getAllLoanRecords();
    }

    @PostMapping("/add")
    public ResponseEntity<?> createLoanRecord(@RequestBody LoanRecordRequestDTO requestDTO) {
        try {
            LoanRecordResponseDTO responseDTO = loanRecordService.createLoanRecord(requestDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException ex) {
            // Trả về JSON có cấu trúc đẹp cho Postman
            Map<String, Object> error = Map.of(
                    "timestamp", LocalDateTime.now(),
                    "status", 400,
                    "error", "Bad Request",
                    "message", ex.getMessage()
            );
            return ResponseEntity.badRequest().body(error);
        }
    }


    @PutMapping("/edit/{loanId}")
    public ResponseEntity<LoanRecordResponseDTO> updateLoanRecord(
            @PathVariable Integer loanId,
            @RequestBody LoanRecordRequestDTO requestDTO) {
        LoanRecordResponseDTO updatedLoanRecord = loanRecordService.updateLoanRecord(loanId, requestDTO);
        return ResponseEntity.ok(updatedLoanRecord);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLoanRecord(@PathVariable Integer id) {
        loanRecordService.deleteLoanRecord(id);
        return ResponseEntity.ok("Phiếu mượn đã được xóa thành công.");
    }

}
