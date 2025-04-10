package com.example.librabry_spring_boot.repository;

import com.example.librabry_spring_boot.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByAuthorName(String authorName);
    // AuthorRepository.java
    Optional<Author> findByAuthorNameIgnoreCase(String authorName);

}
