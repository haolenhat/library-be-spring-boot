package com.example.librabry_spring_boot.repository;

import com.example.librabry_spring_boot.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
    Optional<Publisher> findByPublisherName(String publisherName);
    Optional<Publisher> findByPublisherNameIgnoreCase(String publisherName);
}