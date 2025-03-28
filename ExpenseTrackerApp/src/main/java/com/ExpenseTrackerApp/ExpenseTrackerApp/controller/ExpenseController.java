package com.ExpenseTrackerApp.ExpenseTrackerApp.controller;

import com.ExpenseTrackerApp.ExpenseTrackerApp.exception.ExpenseNotFoundException;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.request.ExpenseDto;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.response.ExpenseResponse;
import com.ExpenseTrackerApp.ExpenseTrackerApp.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    public List<ExpenseResponse> getAllExpenses(){
        log.info("Obtaining all the expenses");
        try{
            return this.expenseService.getAllExpenses();
        }catch (Exception e){
            log.error("Error obtaining all expenses", e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ExpenseResponse getExpenseById(@PathVariable Long id){
        log.info("Obtaining expense with id: {}", id);
        try {
            ExpenseResponse expenseResponse = this.expenseService.getExpenseById(id);
            if (expenseResponse == null){
                throw new ExpenseNotFoundException(id);
            }
            return expenseResponse;
        } catch (ExpenseNotFoundException e) {
            log.error("Bad ID Request",e);
            throw e;
        }
    }

    @PostMapping("/add")
    public void addExpense(@RequestBody /* @Valid */ ExpenseDto expenseDto){
        log.info("Adding expense with description: {}", expenseDto.getDescription());
        try {
            this.expenseService.addExpense(expenseDto);
            log.info("Expense added successfully");
        } catch (Exception e) {
            log.error("Error adding expense: {}", expenseDto.getDescription(), e);
            throw e;
        }
    }

    @PutMapping("/{id}")       //modify or update
    public void updateExpense(@PathVariable Long id,
                              @RequestBody ExpenseDto expenseDto){
        log.info("Modifying expense with id: {}", id);
        try {
            this.expenseService.updateExpense(id,expenseDto);
            log.info("Expense modified with id: {}", id);
        } catch (Exception e) {
            log.error("Error updating expense: {}", expenseDto.getDescription(), e);
            throw e;
        }
        log.info("Expense with id {} modified successfully",id);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id){
        log.info("Deleting expense with id: {}", id);
        try {
            boolean deleted = this.expenseService.deleteExpense(id);
            if (!deleted) {
                throw new ExpenseNotFoundException(id);
            }
            log.info("Expense with id {} deleted successfully", id);
        } catch (ExpenseNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error deleting expense with id: {}", id, e);
            throw e;
        }
    }
}
