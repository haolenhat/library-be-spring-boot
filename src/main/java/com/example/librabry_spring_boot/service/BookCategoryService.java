package com.example.librabry_spring_boot.service;

import com.example.librabry_spring_boot.model.BookCategory;

import java.util.List;

public interface BookCategoryService {
    public void deleteCategory(Integer id);
    public BookCategory updateCategory(Integer id, BookCategory updatedCategory);
    public List<BookCategory> getAllCategories();
    public BookCategory addCategory(BookCategory category);
}
