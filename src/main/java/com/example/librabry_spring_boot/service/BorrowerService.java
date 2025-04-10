package com.example.librabry_spring_boot.service;

import com.example.librabry_spring_boot.model.Borrower;

import java.util.List;

public interface BorrowerService {
    public Borrower addBorrower(Borrower borrower);
    public List<Borrower> getAllBorrowers();
    public void deleteBorrowerByCode(String code);
    public Borrower updateBorrower(String code, Borrower updated);
}
