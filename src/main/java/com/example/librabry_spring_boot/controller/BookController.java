package com.example.librabry_spring_boot.controller;

import com.example.librabry_spring_boot.dto.BookRequest;
import com.example.librabry_spring_boot.model.Book;
import com.example.librabry_spring_boot.service.BookService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody BookRequest request) {
        try {
            Book book = bookService.addBook(request);
            return ResponseEntity.ok().body(Map.of(
                    "message", "Thêm sách thành công!",
                    "book", book
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Đã xảy ra lỗi khi thêm sách."
            ));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable Integer id){
        try{
            bookService.deleteBookById(id);
            return ResponseEntity.ok("Xoá thành công quyển sách với id: "+ id);
        }
        catch (EmptyResultDataAccessException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy quyển sách với id"+ id);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi xoá sách" + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBook();
        return ResponseEntity.ok(books);
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable Integer id, @RequestBody BookRequest request) {
        Book updatedBook = bookService.updateBookById(id, request);
        return ResponseEntity.ok(updatedBook);
    }

    @GetMapping("/count")
    public ResponseEntity<?> countUnavailable(){
        return ResponseEntity.ok(bookService.countUnavailable());
    }
}
