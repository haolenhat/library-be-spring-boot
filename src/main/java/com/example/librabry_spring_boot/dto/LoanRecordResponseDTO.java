package com.example.librabry_spring_boot.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class LoanRecordResponseDTO {
    private Integer loanId;
    private String borrowerCode;
    private String borrowerName;

    private Integer librarianId;
    private String librarianName;

    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private String status;
    private BigDecimal lostOrDamagedFee;

    private List<LoanCardInfo> loanCards;

    // Nested class: LoanCardInfo
    public static class LoanCardInfo {
        private Integer loancardId;
        private Integer bookId;
        private String bookTitle;
        private int quantity;

        public Integer getLoancardId() {
            return loancardId;
        }

        public void setLoancardId(Integer loancardId) {
            this.loancardId = loancardId;
        }

        public Integer getBookId() {
            return bookId;
        }

        public void setBookId(Integer bookId) {
            this.bookId = bookId;
        }

        public String getBookTitle() {
            return bookTitle;
        }

        public void setBookTitle(String bookTitle) {
            this.bookTitle = bookTitle;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    // Constructors
    public LoanRecordResponseDTO() {}

    // Getters and setters
    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public String getBorrowerCode() {
        return borrowerCode;
    }

    public void setBorrowerCode(String borrowerCode) {
        this.borrowerCode = borrowerCode;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public Integer getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(Integer librarianId) {
        this.librarianId = librarianId;
    }

    public String getLibrarianName() {
        return librarianName;
    }

    public void setLibrarianName(String librarianName) {
        this.librarianName = librarianName;
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

    public List<LoanCardInfo> getLoanCards() {
        return loanCards;
    }

    public void setLoanCards(List<LoanCardInfo> loanCards) {
        this.loanCards = loanCards;
    }
}
