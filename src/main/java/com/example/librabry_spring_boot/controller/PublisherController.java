package com.example.librabry_spring_boot.controller;

import com.example.librabry_spring_boot.model.Publisher;
import com.example.librabry_spring_boot.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping("/all")
    public List<Publisher> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPublisher(@RequestBody Publisher publisher) {
        try {
            return ResponseEntity.ok(publisherService.addPublisher(publisher));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updatePublisher(@PathVariable Integer id, @RequestBody Publisher publisher) {
        try {
            return ResponseEntity.ok(publisherService.updatePublisher(id, publisher));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePublisher(@PathVariable Integer id) {
        try {
            publisherService.deletePublisher(id);
            return ResponseEntity.ok("Xoá nhà xuất bản thành công!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
