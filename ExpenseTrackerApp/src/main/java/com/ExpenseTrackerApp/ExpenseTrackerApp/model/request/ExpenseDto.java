package com.ExpenseTrackerApp.ExpenseTrackerApp.model.request;

import com.ExpenseTrackerApp.ExpenseTrackerApp.model.Category;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ExpenseDto {
    private Double amount;
    private LocalDate date;
    private String description;
    private Category category;
    private User user;
    private Long categoryInt;
    private Long userInt;

//    public ExpenseDto(Double amount, LocalDate date, String description, Category category) {
//        this.amount = amount;
//        this.date = date;
//        this.description = description;
//        this.category = category;
//    }

    public ExpenseDto(Double amount, LocalDate date, String description, Category category, User user) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
        this.user=user;
    }

//    public ExpenseDto(Double amount, LocalDate date, String description, Long category, Long user) {
//        this.amount = amount;
//        this.date = date;
//        this.description = description;
//        this.categoryInt = category;
//        this.userInt=user;
//    }
}
