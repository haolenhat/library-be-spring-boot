package com.example.librabry_spring_boot.model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "loan_records")
public class LoanRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loanId;

    @ManyToOne
    @JoinColumn(name = "borrower_code")
    private Borrower borrower;

    @ManyToOne
    @JoinColumn(name = "librarian_id")
    private User librarian;

    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;

    private String status;

    private BigDecimal lostOrDamagedFee;

    @OneToMany(mappedBy = "loanRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoanCard> loanCards = new ArrayList<>();





    public LoanRecord() {
    }

    public LoanRecord(Integer loanId, Borrower borrower, User librarian, LocalDateTime borrowDate, LocalDateTime returnDate, String status, BigDecimal lostOrDamagedFee, List<LoanCard> loanCards) {
        this.loanId = loanId;
        this.borrower = borrower;
        this.librarian = librarian;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
        this.lostOrDamagedFee = lostOrDamagedFee;
        this.loanCards = loanCards;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public User getLibrarian() {
        return librarian;
    }

    public void setLibrarian(User librarian) {
        this.librarian = librarian;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getLostOrDamagedFee() {
        return lostOrDamagedFee;
    }

    public void setLostOrDamagedFee(BigDecimal lostOrDamagedFee) {
        this.lostOrDamagedFee = lostOrDamagedFee;
    }

    public List<LoanCard> getLoanCards() {
        return loanCards;
    }

    public void setLoanCards(List<LoanCard> loanCards) {
        this.loanCards = loanCards;
    }
}
