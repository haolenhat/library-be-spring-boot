package com.example.librabry_spring_boot.repository;

import com.example.librabry_spring_boot.model.OverdueRecord;
import com.example.librabry_spring_boot.model.LoanRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OverdueRecordRepository extends JpaRepository<OverdueRecord, Integer> {

    boolean existsByLoanRecord(LoanRecord loanRecord);
    List<OverdueRecord> findByFineAmountGreaterThan(BigDecimal fine);
}
