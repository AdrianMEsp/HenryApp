package com.ExpenseTrackerApp.ExpenseTrackerApp.controller;



import com.ExpenseTrackerApp.ExpenseTrackerApp.model.request.CategoryDto;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.response.CategoryResponse;
import com.ExpenseTrackerApp.ExpenseTrackerApp.repository.CategoryRepository;
import com.ExpenseTrackerApp.ExpenseTrackerApp.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private CategoryService categoryService;
    private CategoryDto categoryDto1;

    @BeforeEach
    void setUp(){
        categoryDto1 = new CategoryDto("Category 1", "Description category 1");
    }

    @Test
    @DisplayName("Test add a new category")
    void testAddCategory() throws Exception{
        doNothing().when(categoryService).addCategory(any(CategoryDto.class));

        mockMvc.perform(post("/api/category/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto1))
        ).andExpect(status().isOk());

        verify(categoryService,times(1))
                .addCategory(any(CategoryDto.class));
    }

    @Test
    @DisplayName("Test get an existing category with id")
    void testGetCategoryById() throws Exception {
        Long categoryId = 1L;
        CategoryResponse categoryResponse = new CategoryResponse(
                "Category 1", "Description category 1");
        when(categoryService.getCategoryById(any(Long.class)))
                .thenReturn(categoryResponse);

        mockMvc.perform(get("/api/category/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value(categoryResponse.getName()))
                .andExpect(jsonPath("$.description")
                        .value(categoryResponse.getDescription()));

        verify(categoryService, times(1)).getCategoryById(any(Long.class));
    }

    @Test
    @DisplayName("Test get all categories")
    void testGetAllCategories() throws Exception{
        CategoryResponse categoryResponse1 =
                new CategoryResponse("Category 1", "Description category 1");
        CategoryResponse categoryResponse2 =
                new CategoryResponse("Category 2", "Description category 2");

        when(categoryService.getAllCategories())
                .thenReturn(Arrays.asList(categoryResponse1, categoryResponse2));

        mockMvc.perform(get("/api/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value(categoryResponse1.getName()));

        verify(categoryService,times(1))
                .getAllCategories();
    }

    @Test
    @DisplayName("Test update an existing category")
    void testUpdateCategory() throws Exception{
        Long categoryId = 1L;
        CategoryDto categoryDto = new CategoryDto("New name","New description");

        mockMvc.perform(put("/api/category/{id}",categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test delete an existing category")
    void testDeleteExistingCategory() throws Exception{
        Long categoryId = 1L;
        when(categoryService.deleteCategory(eq(categoryId)))
                .thenReturn(true); //returns boolean true if it found it

        mockMvc.perform(delete("/api/category/{id}",categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(categoryService, times(1))
                .deleteCategory(eq(categoryId));
    }

    @Test
    @DisplayName("Test delete an NOT existing category")
    void testDeleteNotExistingCategory() throws Exception{
        Long categoryId = 1L;
        when(categoryService.deleteCategory(eq(categoryId)))
                .thenReturn(false); //returns boolean true if it found it

        mockMvc.perform(delete("/api/category/{id}",categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(categoryService, times(1))
                .deleteCategory(eq(categoryId));
    }

}