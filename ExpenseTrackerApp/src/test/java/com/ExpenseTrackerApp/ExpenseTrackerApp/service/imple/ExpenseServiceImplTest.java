package com.ExpenseTrackerApp.ExpenseTrackerApp.service.imple;

import com.ExpenseTrackerApp.ExpenseTrackerApp.model.Category;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.Expense;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.User;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.request.ExpenseDto;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.response.ExpenseResponse;
import com.ExpenseTrackerApp.ExpenseTrackerApp.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseServiceImplTest {
    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private Expense expense;
    private ExpenseDto expenseDto;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("Andrew Garfield","spiderman@gmail.com");
        MockitoAnnotations.openMocks(this);
        Category category = new Category("Food","Daily food and grocery expenses");
        expense = new Expense(2500.25, LocalDate.of(2025,2,15)
                ,"Drinks with the boys",category);
        expenseDto = new ExpenseDto(1300.25, LocalDate.of(2025,2,18)
                ,"Groceries",category,user);
    }

    @Test
    @DisplayName("Add expense")
    void testAddExpense() {
        when(expenseRepository.save((Expense) any(Expense.class))).thenReturn(expense);

        expenseService.addExpense(expenseDto);

        verify(expenseRepository,times(1))
                .save((Expense) any(Expense.class));
    }

    @Test
    void testGetAllExpenses() {
        List<Expense> expenses = Arrays.asList(expense);
        when(expenseRepository.findAll()).thenReturn(expenses);

        List<ExpenseResponse> expenseResponses = expenseService.getAllExpenses();

        assertNotNull(expenseResponses);
        assertEquals(1,expenseResponses.size());
        assertEquals(expense.getDescription(), expenseResponses.get(0).getDescription());
    }

//    @Test
//    void testUpdateExpense() {
//        when(expenseRepository.save(ArgumentMatchers.any(Category.class))).thenReturn(expense);
//
//        expenseService.updateExpense(1L,expenseDto);
//
//        verify(expenseRepository,times(1))
//                .save(ArgumentMatchers.any(Category.class));
//    }

    @Test
    void testDeleteExpense() {
        doNothing().when(expenseRepository).deleteById(1L);

        expenseService.deleteExpense(1L);

        verify(expenseRepository,times(1))
                .deleteById(1L);
    }

}