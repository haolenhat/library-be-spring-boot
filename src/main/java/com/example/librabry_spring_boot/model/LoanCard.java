package com.example.librabry_spring_boot.model;


import jakarta.persistence.*;

@Entity
@Table(name = "loan_card")
public class LoanCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loancardId;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private LoanRecord loanRecord;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private int quantity;

    public Integer getLoancardId() {
        return loancardId;
    }

    public void setLoancardId(Integer loancardId) {
        this.loancardId = loancardId;
    }

    public LoanRecord getLoanRecord() {
        return loanRecord;
    }

    public void setLoanRecord(LoanRecord loanRecord) {
        this.loanRecord = loanRecord;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LoanCard() {
    }

    public LoanCard(Integer loancardId, LoanRecord loanRecord, Book book, int quantity) {
        this.loancardId = loancardId;
        this.loanRecord = loanRecord;
        this.book = book;
        this.quantity = quantity;
    }
}
