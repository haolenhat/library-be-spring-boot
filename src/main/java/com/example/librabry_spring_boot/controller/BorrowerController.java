package com.example.librabry_spring_boot.controller;

import com.example.librabry_spring_boot.model.Borrower;
import com.example.librabry_spring_boot.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {

    @Autowired
    private BorrowerService borrowerService;

    @PostMapping("/add")
    public ResponseEntity<?> addBorrower(@RequestBody Borrower borrower) {
        return ResponseEntity.ok(borrowerService.addBorrower(borrower));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Borrower>> getAll() {
        return ResponseEntity.ok(borrowerService.getAllBorrowers());
    }

    @DeleteMapping("/delete/{code}")
    public ResponseEntity<?> deleteBorrower(@PathVariable String code) {
        borrowerService.deleteBorrowerByCode(code);
        return ResponseEntity.ok("Xoá người mượn thành công");
    }

    @PutMapping("/edit/{code}")
    public ResponseEntity<Borrower> update(@PathVariable String code, @RequestBody Borrower borrower) {
        return ResponseEntity.ok(borrowerService.updateBorrower(code, borrower));
    }
}
