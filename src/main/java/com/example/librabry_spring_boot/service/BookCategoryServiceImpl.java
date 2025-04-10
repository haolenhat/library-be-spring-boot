package com.example.librabry_spring_boot.service;


import com.example.librabry_spring_boot.model.BookCategory;
import com.example.librabry_spring_boot.repository.BookCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCategoryServiceImpl implements BookCategoryService {

    @Autowired
    private BookCategoryRepository categoryRepository;

    // Thêm thể loại
    public BookCategory addCategory(BookCategory category) {
        if (categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName().trim()).isPresent()) {
            throw new RuntimeException("Tên thể loại đã tồn tại!");
        }
        return categoryRepository.save(category);
    }

    // Lấy danh sách
    public List<BookCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Sửa thể loại
    public BookCategory updateCategory(Integer id, BookCategory updatedCategory) {
        BookCategory existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại với ID: " + id));

        var categoryWithSameName = categoryRepository.findByCategoryNameIgnoreCase(updatedCategory.getCategoryName().trim());
        if (categoryWithSameName.isPresent() && !categoryWithSameName.get().getCategoryId().equals(id)) {
            throw new RuntimeException("Tên thể loại đã tồn tại!");
        }

        existing.setCategoryName(updatedCategory.getCategoryName().trim());
        return categoryRepository.save(existing);
    }

    // Xoá thể loại (chỉ khi không có sách)
    public void deleteCategory(Integer id) {
        BookCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thể loại với ID: " + id));

        if (category.getBooks() != null && !category.getBooks().isEmpty()) {
            throw new RuntimeException("Không thể xoá thể loại vì đang có sách thuộc thể loại này!");
        }

        categoryRepository.deleteById(id);
    }
}
