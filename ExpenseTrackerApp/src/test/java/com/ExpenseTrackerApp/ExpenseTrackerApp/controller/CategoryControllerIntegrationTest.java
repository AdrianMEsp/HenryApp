package com.ExpenseTrackerApp.ExpenseTrackerApp.controller;

import com.ExpenseTrackerApp.ExpenseTrackerApp.model.request.CategoryDto;
import com.ExpenseTrackerApp.ExpenseTrackerApp.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @MockitoBean
    private CategoryService categoryService;
    private CategoryDto categoryDto1;

    @BeforeEach
    void setUp(){
        categoryDto1 = new CategoryDto("Category 1", "Description category 1");
    }

    @Test
    void testAddCategory() throws Exception{
        doNothing().when(categoryService).addCategory(any(CategoryDto.class));

        mockMvc.perform(post("api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto1))
        ).andExpect(status().isOk());

        verify(categoryService,times(1))
                .addCategory(any(CategoryDto.class));
    }

    @Test
    void testGetCategory() throws Exception {
        Long categoryId = 1L;
        doNothing().when(categoryService).getCategoryById(any(Long.class));

        mockMvc.perform(get("/api/category/{id}",categoryId)
                                .contentType(MediaType.APPLICATION_JSON)
                        //.content(objectMapper.writeValueAsString(categoryDto1)))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value(categoryDto1.getName()));


        verify(categoryService,times(1))
                .getCategoryById(any(Long.class));
    }

    @Test
    void testGetAllCategories() throws Exception{
        mockMvc.perform(get("/api/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(categoryDto1.getName()));
    }

    @Test
    void testUpdateCategory() throws Exception{
        Long categoryId = 1L;
        CategoryDto categoryDto = new CategoryDto("New name","New description");

        mockMvc.perform(put("/api/category/{id}",categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCategory() throws Exception{
        Long categoryId = 1L;
        mockMvc.perform(delete("/api/category/{id}",categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}