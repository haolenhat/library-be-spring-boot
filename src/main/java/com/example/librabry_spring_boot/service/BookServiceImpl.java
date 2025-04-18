package com.example.librabry_spring_boot.service;

import com.example.librabry_spring_boot.dto.BookBorrowedCountDTO;
import com.example.librabry_spring_boot.dto.BookRequest;
import com.example.librabry_spring_boot.model.*;
import com.example.librabry_spring_boot.repository.AuthorRepository;
import com.example.librabry_spring_boot.repository.BookCategoryRepository;
import com.example.librabry_spring_boot.repository.BookRepository;
import com.example.librabry_spring_boot.repository.PublisherRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final BookCategoryRepository categoryRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository, BookCategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.categoryRepository = categoryRepository;
    }

    // Thêm 1 quyển sách
    @Transactional
    @Override
    public Book addBook(BookRequest request) {
        // Kiểm tra tên nếu sách đã tồn tại theo title
        if (bookRepository.findByTitle(request.getTitle()).isPresent()) {
            throw new RuntimeException("Tên sách đã tồn tại!");
        }

        // Tìm hoặc tạo Publisher
        Publisher publisher = publisherRepository.findByPublisherNameIgnoreCase(request.getPublisherName())
                .orElseGet(() -> {
                    Publisher newPublisher = new Publisher();
                    // Kiểm tra giá trị null trước khi sử dụng trim()
                    newPublisher.setPublisherName(request.getPublisherName() != null ? request.getPublisherName().trim() : "Unknown Publisher");
                    return publisherRepository.save(newPublisher);
                });

        // Tìm hoặc tạo Book Category
        BookCategory category = categoryRepository.findByCategoryName(request.getCategoryName())
                .orElseGet(() -> {
                    BookCategory newCategory = new BookCategory();
                    newCategory.setCategoryName(request.getCategoryName());
                    return categoryRepository.save(newCategory);
                });

        // Tìm hoặc tạo danh sách Author
        List<Author> authors = new ArrayList<>();
        for (String authorName : request.getAuthorNames()) {
            Author author = authorRepository.findByAuthorName(authorName)
                    .orElseGet(() -> {
                        Author newAuthor = new Author();
                        newAuthor.setAuthorName(authorName);
                        return authorRepository.save(newAuthor);
                    });
            authors.add(author);
        }

        // Tạo Book
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setLinkImg(request.getLinkImg());
        book.setAvailableCopies(request.getAvailableCopies());
        book.setDescription(request.getDescription());
        book.setPublisher(publisher);
        book.setCategory(category);
        book.setAuthors(authors);

        return bookRepository.save(book);
    }

    // API xóa 1 quyển sách theo id
    public void deleteBookById(Integer id) {
        bookRepository.deleteById(id);
    }

    // API lấy danh sách tất cả cuốn sách
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }


    @Transactional
    @Override
    public Book updateBookById(Integer id, BookRequest request) {
        // Tìm sách theo ID
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách với ID: " + id));

        // Cập nhật các trường thông tin
        existingBook.setTitle(request.getTitle());
        existingBook.setLinkImg(request.getLinkImg());
        existingBook.setAvailableCopies(request.getAvailableCopies());
        existingBook.setDescription(request.getDescription());

        // Xử lý Publisher
        Publisher publisher = publisherRepository.findByPublisherNameIgnoreCase(request.getPublisherName())
                .orElseGet(() -> {
                    Publisher newPublisher = new Publisher();
                    newPublisher.setPublisherName(request.getPublisherName());
                    return publisherRepository.save(newPublisher);
                });
        existingBook.setPublisher(publisher);

        // Xử lý Category
        BookCategory category = categoryRepository.findByCategoryName(request.getCategoryName())
                .orElseGet(() -> {
                    BookCategory newCategory = new BookCategory();
                    newCategory.setCategoryName(request.getCategoryName());
                    return categoryRepository.save(newCategory);
                });
        existingBook.setCategory(category);

        // Xử lý danh sách tác giả
        List<Author> authors = new ArrayList<>();
        for (String authorName : request.getAuthorNames()) {
            Author author = authorRepository.findByAuthorName(authorName)
                    .orElseGet(() -> {
                        Author newAuthor = new Author();
                        newAuthor.setAuthorName(authorName);
                        return authorRepository.save(newAuthor);
                    });
            authors.add(author);
        }
        existingBook.setAuthors(authors);

        // Lưu thông tin đã cập nhật
        return bookRepository.save(existingBook);
    }

    @Override
    public List<BookBorrowedCountDTO> countUnavailable() {
        return bookRepository.countUnavailable();
    }
}
