package com.ExpenseTrackerApp.ExpenseTrackerApp.service;

import com.ExpenseTrackerApp.ExpenseTrackerApp.model.request.CategoryDto;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryDto categoryDto);

    List<CategoryResponse> getAllCategories();

    void updateCategory(Long id, CategoryDto categoryDto);

    boolean deleteCategory(Long id);

    CategoryResponse getCategoryById(Long id);
}
