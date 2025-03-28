package com.ExpenseTrackerApp.ExpenseTrackerApp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    private Expense expense1, expense2;

    @BeforeEach
    public void setUp() {
        user = new User("Adrian","adrianmail@gmail.com");

        Category categoryFood = new Category("Food","Daily food and grocery expenses");

        expense1 = new Expense(2500.00,
                LocalDate.of(2025,2,20),
                "Hamburger and Pizza with The Boys",categoryFood);

        expense2 = new Expense(300.00,
                LocalDate.of(2025,2,27),
                "Tacos and Beers with GF",categoryFood);
    }

    @Test
    @DisplayName("Add an expense")
    void testAddExpense(){
        assertEquals(0,user.getExpenses().size());
        this.user.addExpense(expense1);
        assertEquals(1,user.getExpenses().size());
    }

    @Test
    @DisplayName("Get all expenses")
    void testGetExpenses() {
        assertEquals(0,this.user.getExpenses().size());
        this.user.addExpense(expense1);
        this.user.addExpense(expense2);
        assertEquals(2,this.user.getExpenses().size());
    }

    @Test
    @DisplayName("Expenses by date in order")
    void testGetExpensesByDates(){
        LocalDate date1,date2;
        date1=LocalDate.of(2025,2,15);
        date2=LocalDate.of(2025,2,21);
        assertNull(this.user.getExpensesByDates(date1,date2));
        this.user.addExpense(expense1);
        this.user.addExpense(expense2);
        assertEquals(1,this.user.getExpensesByDates(date2,date1).size());
    }

    @Test
    @DisplayName("Expenses by date in disorder")
    void testGetExpensesByDatesDisorder(){
        LocalDate date1,date2;
        date1=LocalDate.of(2025,2,21);
        date2=LocalDate.of(2025,2,15);
        assertNull(this.user.getExpensesByDates(date1,date2));
        this.user.addExpense(expense1);
        this.user.addExpense(expense2);
        assertEquals(1,this.user.getExpensesByDates(date2,date1).size());
    }

}