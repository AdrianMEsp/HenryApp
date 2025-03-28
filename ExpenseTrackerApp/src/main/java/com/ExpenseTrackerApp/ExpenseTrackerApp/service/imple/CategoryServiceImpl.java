package com.ExpenseTrackerApp.ExpenseTrackerApp.service.imple;

import com.ExpenseTrackerApp.ExpenseTrackerApp.model.Category;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.request.CategoryDto;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.response.CategoryResponse;
import com.ExpenseTrackerApp.ExpenseTrackerApp.repository.CategoryRepository;
import com.ExpenseTrackerApp.ExpenseTrackerApp.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void addCategory(CategoryDto categoryDto){
        Category category = mapToCategory(categoryDto);
        this.categoryRepository.save(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return this.categoryRepository.findAll().stream()
                .map(this::mapToCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void updateCategory(Long id, CategoryDto categoryDto){
        Category category = mapToCategory(categoryDto);
        category.setId(id);
        this.categoryRepository.save(category);
    }

    @Override
    public boolean deleteCategory(Long id) {
        if (categoryRepository.findById(id).isPresent()){
            this.categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        if (this.categoryRepository.findById(id).isPresent()) {
            Category category = this.categoryRepository.findById(id).get();
            return mapToCategoryResponse(category);
        }
        return null;
    }

    private Category mapToCategory(CategoryDto categoryDto) {
        return new Category(
                categoryDto.getName(), categoryDto.getDescription());
    }

    private CategoryResponse mapToCategoryResponse(Category category){
        return new CategoryResponse(category.getName(), category.getDescription());
    }
}
