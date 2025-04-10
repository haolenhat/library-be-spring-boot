package com.example.librabry_spring_boot.repository;

import com.example.librabry_spring_boot.model.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Integer> {
    Optional<BookCategory> findByCategoryName(String categoryName);
    Optional<BookCategory> findByCategoryNameIgnoreCase(String categoryName);
}