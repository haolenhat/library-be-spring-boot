package com.example.librabry_spring_boot.repository;


import com.example.librabry_spring_boot.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowerRepository extends JpaRepository<Borrower, String> {
    Optional<Borrower> findByBorrowerCode(String borrowerCode);
}
