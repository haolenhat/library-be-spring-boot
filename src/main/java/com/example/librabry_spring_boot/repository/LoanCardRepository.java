package com.example.librabry_spring_boot.repository;

import com.example.librabry_spring_boot.model.LoanCard;
import com.example.librabry_spring_boot.model.LoanRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanCardRepository extends JpaRepository<LoanCard, Integer> {
    List<LoanCard> findByLoanRecord(LoanRecord loanRecord);
}
