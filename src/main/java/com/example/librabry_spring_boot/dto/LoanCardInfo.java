package com.example.librabry_spring_boot.dto;

public class LoanCardInfo {
    private Integer loancardId;
    private String bookTitle;
    private int quantity;

    // ✅ Phải có getter/setter tương ứng:
    public Integer getLoancardId() {
        return loancardId;
    }

    public void setLoancardId(Integer loancardId) {
        this.loancardId = loancardId;
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
