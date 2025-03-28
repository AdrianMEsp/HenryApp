package com.ExpenseTrackerApp.ExpenseTrackerApp.service;

import com.ExpenseTrackerApp.ExpenseTrackerApp.model.Expense;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.request.ExpenseDto;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.response.ExpenseResponse;

import java.util.List;
import java.util.function.Predicate;

public interface ExpenseService {
    void addExpense(ExpenseDto expenseDto);

    List<ExpenseResponse> getAllExpenses();

    ExpenseResponse getExpenseById(Long id);

    void updateExpense(Long id, ExpenseDto expenseDto);

    boolean deleteExpense(Long id);

    double calculateTotal(List<ExpenseDto> expensesDto);

    //cambiar expense to expenseResponse
    List<Expense> filterExpenses(List<Expense> expenses, Predicate<Expense> criteria);
}
