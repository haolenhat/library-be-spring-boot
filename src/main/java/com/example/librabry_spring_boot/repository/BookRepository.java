package com.example.librabry_spring_boot.repository;

import com.example.librabry_spring_boot.dto.BookBorrowedCountDTO;
import com.example.librabry_spring_boot.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByTitle(String title);
    List<Book> findByTitleContainingIgnoreCase(String keyword);

    @Query("SELECT new com.example.librabry_spring_boot.dto.BookBorrowedCountDTO(" +
            "b.bookId, b.title, b.availableCopies, SUM(lc.quantity)) " +
            "FROM Book b " +
            "JOIN b.loanCards lc " +
            "JOIN lc.loanRecord lr " +
            "WHERE lr.status != 'Returned' " +
            "GROUP BY b.bookId, b.title, b.availableCopies")
    List<BookBorrowedCountDTO> countUnavailable();



}

