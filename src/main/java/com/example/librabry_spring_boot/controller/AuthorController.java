package com.example.librabry_spring_boot.controller;

import com.example.librabry_spring_boot.model.Author;
import com.example.librabry_spring_boot.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping("/add")
    public ResponseEntity<?> createAuthor(@RequestBody Author author) {
        try {
            Author newAuthor = authorService.addAuthor(author);
            return ResponseEntity.ok(newAuthor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Author>> getAllAuthor(){
        List<Author> authors = authorService.getAllAuthor();
        return ResponseEntity.ok(authors);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Integer id) {
        try {
            authorService.deleteAuthorById(id);
            return ResponseEntity.ok("Xóa tác giả thành công!");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Integer id, @RequestBody Author author) {
        try {
            Author updated = authorService.updateAuthor(id, author);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
