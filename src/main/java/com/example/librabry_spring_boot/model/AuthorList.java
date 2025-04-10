package com.example.librabry_spring_boot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "authors_list")
public class AuthorList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer listAuthorId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public AuthorList() {
    }

    public AuthorList(Integer listAuthorId, Book book, Author author) {
        this.listAuthorId = listAuthorId;
        this.book = book;
        this.author = author;
    }

    public Integer getListAuthorId() {
        return listAuthorId;
    }

    public void setListAuthorId(Integer listAuthorId) {
        this.listAuthorId = listAuthorId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
