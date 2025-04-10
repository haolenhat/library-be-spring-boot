package com.example.librabry_spring_boot.model;


import jakarta.persistence.*;

@Entity
@Table(name = "borrowers")
public class Borrower {

    @Id
    @Column(name = "borrower_code", length = 20)
    private String borrowerCode;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    public Borrower() {
    }

    public Borrower(String borrowerCode, String fullName) {
        this.borrowerCode = borrowerCode;
        this.fullName = fullName;
    }

    public String getBorrowerCode() {
        return borrowerCode;
    }

    public void setBorrowerCode(String borrowerCode) {
        this.borrowerCode = borrowerCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
