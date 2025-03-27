package com.ExpenseTrackerApp.ExpenseTrackerApp.controller;

import com.ExpenseTrackerApp.ExpenseTrackerApp.model.Category;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.User;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.request.ExpenseDto;
import com.ExpenseTrackerApp.ExpenseTrackerApp.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExpenseService expenseService;
    private Category category;
    private ExpenseDto expense1, expense2;
    private ObjectMapper objectMapper;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("Andrew Garfield","spiderman@gmail.com");
        category = new Category("Food", "Groceries");
        expense1 = new ExpenseDto(2500.99,
                LocalDate.of(2025, 1, 27),
                "Credit card",
                category,user
        );
        expense2 = new ExpenseDto(1500.10,
                LocalDate.of(2025, 1, 27),
                "Credit card",
                category,user
        );
    }

    @Test
    public void testAddExpense() throws Exception {
        doNothing().when(expenseService).addExpense(any(ExpenseDto.class));

        mockMvc.perform(post("api/expenses/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expense1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount")
                        .value(expense1.getAmount()))
                .andExpect(jsonPath("$.category.name")
                        .value(expense1.getCategory().getName()))
        ;
        verify(expenseService, times(1))
                .addExpense(any(ExpenseDto.class));
    }

    @Test
    public void testUpdateExpense() throws Exception {
        Long expenseId = 1L;
        doNothing().when(expenseService).updateExpense(eq(expenseId),
                any(ExpenseDto.class));

        mockMvc.perform(put("api/expenses/{id}", expenseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expense2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount")
                        .value(expense2.getAmount()))
                .andExpect(jsonPath("$.category.name")
                        .value(expense2.getCategory().getName()))
        ;
        verify(expenseService, times(1))
                .updateExpense(1L, any(ExpenseDto.class));
    }

    @Test
    public void testDeleteExpense() throws Exception {
        Long expenseId = 1L;
        mockMvc.perform(delete("/api/expenses/{id}",expenseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllExpenses() throws Exception {
        List<ExpenseDto> responseMock = List.of(expense1, expense2);

        when(expenseService.getAllExpenses());//.thenReturn(responseMock);

        mockMvc.perform(get("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].description")
                        .value(expense1.getDescription()))
                .andExpect(jsonPath("$[1].description")
                        .value(expense2.getDescription()))
        ;
    }

}