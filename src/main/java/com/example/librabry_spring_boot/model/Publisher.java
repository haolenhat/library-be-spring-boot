package com.example.librabry_spring_boot.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer publisherId;

    @Column(nullable = false, unique = true)
    private String publisherName;

    @OneToMany(mappedBy = "publisher")
    @JsonIgnore
    private List<Book> books;

    public Publisher() {
    }

    public Publisher(Integer publisherId, String publisherName, List<Book> books) {
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.books = books;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
