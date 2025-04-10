package com.example.librabry_spring_boot.service;

import com.example.librabry_spring_boot.dto.BookRequest;
import com.example.librabry_spring_boot.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookService {
    Book addBook(BookRequest bookRequest);
    public void deleteBookById(Integer id);
    public List<Book> getAllBook();
    public Book updateBook(Integer id, Book updatedBook);
}
