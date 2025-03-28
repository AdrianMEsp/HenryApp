package com.ExpenseTrackerApp.ExpenseTrackerApp.service.imple;

import com.ExpenseTrackerApp.ExpenseTrackerApp.exception.CategoryNotFoundException;
import com.ExpenseTrackerApp.ExpenseTrackerApp.exception.ExpenseNotFoundException;
import com.ExpenseTrackerApp.ExpenseTrackerApp.exception.UserNotFoundException;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.Category;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.Expense;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.User;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.request.ExpenseDto;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.response.ExpenseResponse;
import com.ExpenseTrackerApp.ExpenseTrackerApp.repository.CategoryRepository;
import com.ExpenseTrackerApp.ExpenseTrackerApp.repository.ExpenseRepository;
import com.ExpenseTrackerApp.ExpenseTrackerApp.repository.UserRepository;
import com.ExpenseTrackerApp.ExpenseTrackerApp.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    @Autowired
    public ExpenseServiceImpl ( ExpenseRepository expenseRepository,
                                UserRepository userRepository,
                                CategoryRepository categoryRepository){
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ExpenseResponse> getAllExpenses(){
        return this.expenseRepository.findAll().stream()
                .map(this::mapToExpenseResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ExpenseResponse getExpenseById(Long id) {
        Optional<Expense> expense = this.expenseRepository.findById(id);
        if ( expense.isPresent()){
            return mapToExpenseResponse(expense.get());
        }
        return null;
    }

    @Override
    public void addExpense(ExpenseDto expenseDto){
        // verify if the user exist
        User user = userRepository.findById(expenseDto.getUser().getId())
                .orElseThrow(()
                        -> new UserNotFoundException(expenseDto.getUser().getId()));

        // verify if the category exist
        Category category = categoryRepository.findById(expenseDto.getCategory().getId())
                .orElseThrow(()
                        -> new CategoryNotFoundException(expenseDto.getCategory().getId()));

        Expense expense =  mapToExpense(expenseDto);
        expenseRepository.save(expense);
        log.info("Expense successfully added with ID: {}", expense.getId());
    }

    @Override
    public void updateExpense(Long id , ExpenseDto expenseDto){
        //verify if the expense exist
        Expense findExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException(id));

        // verify if the user exist
        User user = userRepository.findById(expenseDto.getUser().getId())
                .orElseThrow(()
                        -> new UserNotFoundException(expenseDto.getUser().getId()));

        // verify if the category exist
        Category category = categoryRepository.findById(expenseDto.getCategory().getId())
                .orElseThrow(()
                        -> new CategoryNotFoundException(expenseDto.getCategory().getId()));
        Expense expense = mapToExpense(expenseDto);
        expense.setId(id);
        this.expenseRepository.save(expense);
    }

    @Override
    public boolean deleteExpense(Long id) {
        if (expenseRepository.findById(id).isPresent()){
            this.expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public double calculateTotal(List<ExpenseDto> expensesDto) {
        return expensesDto.stream()
                .mapToDouble(e -> e.getAmount()).sum();
    }

    @Override
    public List<Expense> filterExpenses(List<Expense> expenses, Predicate<Expense> criteria) {
        return expenses.stream().filter(criteria).collect(Collectors.toList());
    }

    private Expense mapToExpense(ExpenseDto expenseDto) {
        return new Expense( expenseDto.getAmount(),
                expenseDto.getDate(),
                expenseDto.getDescription(),
                expenseDto.getCategory(),
                expenseDto.getUser()
                );
    }

    private ExpenseResponse mapToExpenseResponse(Expense expense){
        return new ExpenseResponse(
                expense.getAmount(),
                expense.getDate(),
                expense.getDescription(),
                expense.getCategory());
    }
}
