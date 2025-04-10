package com.example.librabry_spring_boot.service;

import com.example.librabry_spring_boot.model.Author;
import com.example.librabry_spring_boot.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl  implements AuthorService{

    @Autowired
    private AuthorRepository authorRepository;

    //thêm author
    public Author addAuthor(Author author) {
        String name = author.getAuthorName().trim(); // loại bỏ khoảng trắng

        if (authorRepository.findByAuthorNameIgnoreCase(name).isPresent()) {
            throw new RuntimeException("Tác giả đã tồn tại!");
        }

        author.setAuthorName(name); // Cập nhật lại tên sau khi trim
        return authorRepository.save(author);
    }

    //lấy danh sách author
    public List<Author> getAllAuthor(){
        return authorRepository.findAll();
    }

//xoá author
    public void deleteAuthorById(Integer id) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isEmpty()) {
            throw new RuntimeException("Không tìm thấy tác giả với ID: " + id);
        }

        Author author = authorOptional.get();

        // Kiểm tra xem tác giả có sách nào không
        if (author.getBooks() != null && !author.getBooks().isEmpty()) {
            throw new RuntimeException("Tác giả đang có sách, không thể xóa!");
        }

        authorRepository.deleteById(id);
    }


    // sửa thông tin tác giả
    public Author updateAuthor(Integer id, Author updatedAuthor) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác giả với ID: " + id));

        // Kiểm tra tên mới có trùng với tác giả khác không
        Optional<Author> authorWithSameName = authorRepository.findByAuthorNameIgnoreCase(updatedAuthor.getAuthorName().trim());
        if (authorWithSameName.isPresent() && !authorWithSameName.get().getAuthorId().equals(id)) {
            throw new RuntimeException("Tên tác giả đã tồn tại!");
        }

        existingAuthor.setAuthorName(updatedAuthor.getAuthorName().trim());
        return authorRepository.save(existingAuthor);
    }


}
