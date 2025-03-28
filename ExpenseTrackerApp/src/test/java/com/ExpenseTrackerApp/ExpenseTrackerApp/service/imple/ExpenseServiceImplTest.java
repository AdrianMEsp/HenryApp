package com.ExpenseTrackerApp.ExpenseTrackerApp.service.imple;

import com.ExpenseTrackerApp.ExpenseTrackerApp.model.Category;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.Expense;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.User;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.request.ExpenseDto;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.response.ExpenseResponse;
import com.ExpenseTrackerApp.ExpenseTrackerApp.repository.CategoryRepository;
import com.ExpenseTrackerApp.ExpenseTrackerApp.repository.ExpenseRepository;
import com.ExpenseTrackerApp.ExpenseTrackerApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseServiceImplTest {
    @Mock
    private ExpenseRepository expenseRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private Expense expense;
    private ExpenseDto expenseDto;
    private User user;
    private Category category;

    @BeforeEach
    void setUp() {
        user = new User("Andrew Garfield","spiderman@gmail.com");
        MockitoAnnotations.openMocks(this);
        Category category = new Category("Food","Daily food and grocery expenses");
        expense = new Expense(2500.25, LocalDate.of(2025,2,15)
                ,"Drinks with the boys",category);
        expenseDto = new ExpenseDto(1300.25, LocalDate.of(2025,2,18)
                ,"Groceries",category,user);
        category = new Category("Category name","Category description");
    }


    @Test
    @DisplayName("Add expense with existing User and Category")
    void testAddExpense() {
        User userToAdd = new User("Marino Juan","marinojuan@unmail.com");
        userToAdd.setId(1L);

        Category categoryToAdd = new Category("Category 2","description");
        categoryToAdd.setId(2L);

        ExpenseDto expenseDto2 = new ExpenseDto(
            100.0,
            LocalDate.of(2025, 3, 28),
            "Buy books",
                categoryToAdd,
                userToAdd
        );

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(userToAdd));

        when(categoryRepository.findById(2L))
                .thenReturn(Optional.of(categoryToAdd));

        //Do the MapToExpenseResponse
        Expense expense2 = new Expense(
                expenseDto2.getAmount(),
                expenseDto2.getDate(),
                expenseDto2.getDescription(),
                categoryToAdd
        );

        when(expenseRepository.save(expense2))
                .thenReturn(expense2);

        expenseService.addExpense(expenseDto2);

        verify(userRepository).findById(1L);
        verify(categoryRepository).findById(2L);
        verify(expenseRepository,times(1))
                .save(any(Expense.class));
    }


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
    void testGetExpenseById(){
        Long expenseId = 1L;

        when(expenseRepository.findById(eq(expenseId)))
                .thenReturn(Optional.of(expense));

        ExpenseResponse result = expenseService.getExpenseById(expenseId);
        verify(expenseRepository, times(1))
                .findById(eq(expenseId));
        assertNotNull(result);
    }

    @Test
    @DisplayName("Test update existing expense with existing user and category")
    void testUpdateExpense() {
        Long expenseId = 1L;

        User existingUser = new User("Marino Juan", "marinojuan@unmail.com");
        existingUser.setId(1L);

        Category existingCategory = new Category("Category 2", "description");
        existingCategory.setId(2L);

        Expense existingExpense =
                new Expense(50.0,
                        LocalDate.of(2025, 3, 28),
                        "Old description",
                        existingCategory,
                        existingUser);
        existingExpense.setId(expenseId);

        ExpenseDto updatedExpenseDto = new ExpenseDto(
                100.0,
                LocalDate.of(2025, 4, 1),
                "Updated description",
                existingCategory,
                existingUser
        );

        when(expenseRepository.findById(expenseId))
                .thenReturn(Optional.of(existingExpense));
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(existingUser));
        when(categoryRepository.findById(2L))
                .thenReturn(Optional.of(existingCategory));
        when(expenseRepository.save(any(Expense.class)))
                .thenReturn(existingExpense);


        expenseService.updateExpense(expenseId, updatedExpenseDto);


        verify(expenseRepository, times(1))
                .findById(expenseId);
        verify(userRepository, times(1))
                .findById(1L);
        verify(categoryRepository, times(1))
                .findById(2L);
        verify(expenseRepository, times(1))
                .save(any(Expense.class));
    }

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