package com.example.librabry_spring_boot.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "overdue_records")
public class OverdueRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer overdueId;

    @OneToOne
    @JoinColumn(name = "loan_id", referencedColumnName = "loanId", unique = true)
    private LoanRecord loanRecord;

    private LocalDate dueDate;

    private LocalDateTime returnDate;


    private Integer overdueDays;

    private BigDecimal fineAmount;

    public Integer getOverdueId() {
        return overdueId;
    }

    public void setOverdueId(Integer overdueId) {
        this.overdueId = overdueId;
    }

    public LoanRecord getLoanRecord() {
        return loanRecord;
    }

    public void setLoanRecord(LoanRecord loanRecord) {
        this.loanRecord = loanRecord;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(Integer overdueDays) {
        this.overdueDays = overdueDays;
    }

    public BigDecimal getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(BigDecimal fineAmount) {
        this.fineAmount = fineAmount;
    }
}
