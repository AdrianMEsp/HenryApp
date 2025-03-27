package com.ExpenseTrackerApp.ExpenseTrackerApp.model.response;

import com.ExpenseTrackerApp.ExpenseTrackerApp.model.Category;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
public class ExpenseResponse {
    private Double amount;
    private LocalDate date;
    private String description;
    private Category category;

    public ExpenseResponse(Double amount, LocalDate date, String description, Category category) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
    }
}
