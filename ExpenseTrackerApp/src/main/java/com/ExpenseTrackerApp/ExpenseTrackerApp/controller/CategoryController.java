package com.ExpenseTrackerApp.ExpenseTrackerApp.controller;

import com.ExpenseTrackerApp.ExpenseTrackerApp.exception.CategoryNotFoundException;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.request.CategoryDto;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.response.CategoryResponse;
import com.ExpenseTrackerApp.ExpenseTrackerApp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> getAllCategories(){
        log.info("Obtaining all categories");
        try {
            return this.categoryService.getAllCategories();
        } catch (Exception e) {
            log.error("Error obtaining category");
            throw e;
        }
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategory(@PathVariable Long id){
        log.info("Obtaining category with id: {}", id);
        try {
            CategoryResponse categoryResponse = this.categoryService.getCategoryById(id);
            if (categoryResponse == null){
                throw new CategoryNotFoundException(id);
            }
            return categoryResponse;
        } catch (CategoryNotFoundException e) {
            log.error("Bad ID Request", e);
            throw e;
        }
    }

    @PostMapping("/add")
    public void addCategory(@RequestBody CategoryDto categoryDto){
        log.info("Adding category with description: {}", categoryDto.getDescription());
        try {
            this.categoryService.addCategory(categoryDto);
            log.info("Category added successfully");
        } catch (Exception e) {
            log.error("Error adding category {}",categoryDto.getDescription());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public void updateCategory(@PathVariable Long id,
                               @RequestBody CategoryDto categoryDto){
        log.info("Modifying category with id: {}", id);
        try {
            this.categoryService.updateCategory(id,categoryDto);
            log.info("Category with id {} modified successfully",id);
        } catch (Exception e) {
            log.error("Error modifying category with id {}",id);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        log.info("Deleting category with id: {}", id);
        try {
            boolean deleted = this.categoryService.deleteCategory(id);
            if (! deleted){
                throw new CategoryNotFoundException(id);
            }
            log.info("Category with id {} deleted successfully", id);
        } catch (CategoryNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error deleting category");
            throw e;
        }
    }
}
