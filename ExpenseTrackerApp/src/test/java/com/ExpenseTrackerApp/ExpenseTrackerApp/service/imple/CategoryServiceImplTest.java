package com.ExpenseTrackerApp.ExpenseTrackerApp.service.imple;

import com.ExpenseTrackerApp.ExpenseTrackerApp.model.Category;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.request.CategoryDto;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.response.CategoryResponse;
import com.ExpenseTrackerApp.ExpenseTrackerApp.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryDto = new CategoryDto("Food","Daily food and grocery expenses");
        category = new Category(1L,"Food","Daily food and grocery expenses");
    }

    @Test
    @DisplayName("Adding a new category")
    void testAddCategory() {
        //given
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        //when
        categoryService.addCategory(categoryDto);
        //then
        verify(categoryRepository,times(1))
                .save(any(Category.class));
        //addCategory modify the class of the category with a mapToCategory, that's because it changes
    }

    @Test
    @DisplayName("Gets all categories")
    void testGetAllCategories() {
        List<Category> categories = Arrays.asList(category);
        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryResponse> categoryResponses = categoryService.getAllCategories();

        assertNotNull(categoryResponses);
        assertEquals(1,categoryResponses.size());
        assertEquals(category.getName(), categoryResponses.get(0).getName());
    }

    @Test
    @DisplayName("Update an existent category")
    void testUpdateCategoryExist() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        categoryService.updateCategory(1L,categoryDto);

        verify(categoryRepository,times(1))
                .save(any(Category.class));
    }

    @Test
    @DisplayName("Delete an existent category")
    void testDeleteCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        categoryService.deleteCategory(1L);

        verify(categoryRepository,times(1))
                .deleteById(1L);
    }

    @Test
    @DisplayName("Get Category of existing id")
    void testGetCategoryById() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        CategoryResponse response = categoryService.getCategoryById(1L);

        //verify(categoryRepository,times(1)).findById(1L);
        assertEquals(response.getName(),category.getName());
    }

    @Test
    @DisplayName("Get Category of NOT existing id")
    void testGetCategoryByIdNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        CategoryResponse response = categoryService.getCategoryById(1L);

        //verify(categoryRepository,times(1)).findById(1L);
        assertNull(response);
    }

}