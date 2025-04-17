package com.example.librabry_spring_boot.dto;

public class BookBorrowedCountDTO {
    private Integer bookId;
    private String title;
    private Integer availableCopies; // số lượng sách có trong thư viện
    private Long borrowedCopies; // số lượng sách đã được mượn của 1 quyển sách

    // Có thể thêm constructor rỗng
    public BookBorrowedCountDTO() {
    }

    // Constructor để sử dụng với native query
    public BookBorrowedCountDTO(Integer bookId, String title, Integer availableCopies, Long borrowedCopies) {
        this.bookId = bookId;
        this.title = title;
        this.availableCopies = availableCopies;
        this.borrowedCopies = borrowedCopies;
    }

    // Getters và Setters
    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

    public Long getBorrowedCopies() {
        return borrowedCopies;
    }

    public void setBorrowedCopies(Long borrowedCopies) {
        this.borrowedCopies = borrowedCopies;
    }
}