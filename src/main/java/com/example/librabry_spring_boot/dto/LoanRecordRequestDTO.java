package com.example.librabry_spring_boot.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class LoanRecordRequestDTO {
    private String borrowerCode;
    private String borrowerName;
    private Integer librarianId;
    private LocalDateTime borrowDate;
    private String status;
    private LocalDateTime returnDate;
    private BigDecimal lostOrDamagedFee;
    private List<LoanCardInput> loanCards;


    public LoanRecordRequestDTO(String borrowerCode, String borrowerName, Integer librarianId, LocalDateTime borrowDate, String status, LocalDateTime returnDate, BigDecimal lostOrDamagedFee, List<LoanCardInput> loanCards) {
        this.borrowerCode = borrowerCode;
        this.borrowerName = borrowerName;
        this.librarianId = librarianId;
        this.borrowDate = borrowDate;
        this.status = status;
        this.returnDate = returnDate;
        this.lostOrDamagedFee = lostOrDamagedFee;
        this.loanCards = loanCards;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public static class LoanCardInput {
        private Integer bookId;
        private int quantity;

        public Integer getBookId() {
            return bookId;
        }

        public void setBookId(Integer bookId) {
            this.bookId = bookId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    public LoanRecordRequestDTO() {
    }

    public LoanRecordRequestDTO(String borrowerCode, Integer librarianId, LocalDateTime borrowDate, String status, LocalDateTime returnDate, BigDecimal lostOrDamagedFee, List<LoanCardInput> loanCards) {
        this.borrowerCode = borrowerCode;
        this.librarianId = librarianId;
        this.borrowDate = borrowDate;
        this.status = status;
        this.returnDate = returnDate;
        this.lostOrDamagedFee = lostOrDamagedFee;
        this.loanCards = loanCards;
    }

    public String getBorrowerCode() {
        return borrowerCode;
    }

    public void setBorrowerCode(String borrowerCode) {
        this.borrowerCode = borrowerCode;
    }

    public Integer getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(Integer librarianId) {
        this.librarianId = librarianId;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public BigDecimal getLostOrDamagedFee() {
        return lostOrDamagedFee;
    }

    public void setLostOrDamagedFee(BigDecimal lostOrDamagedFee) {
        this.lostOrDamagedFee = lostOrDamagedFee;
    }

    public List<LoanCardInput> getLoanCards() {
        return loanCards;
    }

    public void setLoanCards(List<LoanCardInput> loanCards) {
        this.loanCards = loanCards;
    }
}
