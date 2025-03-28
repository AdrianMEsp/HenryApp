package com.ExpenseTrackerApp.ExpenseTrackerApp.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryResponse {
    private String name;
    private String description;

    public CategoryResponse(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
