package com.example.librabry_spring_boot.controller;

import com.example.librabry_spring_boot.dto.UserResponseDTO;
import com.example.librabry_spring_boot.dto.UserUpdateDTO;
import com.example.librabry_spring_boot.model.User;
import com.example.librabry_spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // API đăng ký
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.register(user);
        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUser(){
        List<UserResponseDTO> users = userService.getAllUserDTO();
        return ResponseEntity.ok(users);
    }

    // API đăng nhập
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> requestBody) {
        String phone = requestBody.get("phone");
        String password = requestBody.get("password");

        UserResponseDTO userDTO = userService.login(phone, password);
        return ResponseEntity.ok(userDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
       try{
           userService.deleteUser(id);
           return ResponseEntity.ok("Đã xoá người dùng thành công !");
       }
       catch (RuntimeException ex){
           return ResponseEntity
                   .badRequest()
                   .body(ex.getMessage());
       }
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }



}