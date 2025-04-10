package com.example.librabry_spring_boot.service;

import com.example.librabry_spring_boot.model.Author;

import java.util.List;

public interface AuthorService {
    public Author addAuthor(Author author);
    public List<Author> getAllAuthor();
    public void deleteAuthorById(Integer id);
    public Author updateAuthor(Integer id, Author updatedAuthor);
}
