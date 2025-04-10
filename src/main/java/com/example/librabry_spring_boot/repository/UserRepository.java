package com.example.librabry_spring_boot.repository;

import com.example.librabry_spring_boot.model.Role;
import com.example.librabry_spring_boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    Optional<User> findByRole(Role role);
}