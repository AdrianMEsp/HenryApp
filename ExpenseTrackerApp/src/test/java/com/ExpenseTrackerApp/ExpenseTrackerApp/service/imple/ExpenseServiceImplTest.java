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
import java.util.Optional;

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

    /*
    @Test
    @DisplayName("Add expense")//no anda hasta que el profe me ayude con ExpenseController
    void testAddExpense() {
        when(expenseRepository.save(ExpenseDto.class))
                .thenReturn(expenseDto);

        expenseService.addExpense(expenseDto);

        verify(expenseRepository,times(1))
                .save((Expense) any(Expense.class));
    }
    */

    @Test
    @DisplayName("Test get all expenses")
    void testGetAllExpenses() {
        List<Expense> expenses = Arrays.asList(expense);
        when(expenseRepository.findAll()).thenReturn(expenses);

        List<ExpenseResponse> expenseResponses = expenseService.getAllExpenses();

        assertNotNull(expenseResponses);
        assertEquals(1,expenseResponses.size());
        assertEquals(expense.getDescription(), expenseResponses.get(0).getDescription());
    }
    @Test
    @DisplayName("Test get an existing expense by id")
    void getExpenseById(){
        Long expenseId = 1L;
        List<Expense> expenses = Arrays.asList(expense);
        when(expenseRepository.findById(eq(expenseId)))
                .thenReturn(Optional.of(expense));

        verify(expenseRepository, times(1))
                .findById(eq(expenseId));
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
    @DisplayName("Test delete existing expense")
    void testDeleteExpense() {
        Long expenseId = 1L;
        Optional<Expense> optionalExpense = Optional.of(new Expense());
        when(expenseRepository.findById(eq(expenseId)))
                .thenReturn(optionalExpense);

        doNothing().when(expenseRepository).deleteById(eq(expenseId));

        expenseService.deleteExpense(expenseId);

        verify(expenseRepository,times(1))
                .deleteById(eq(expenseId));
    }

}