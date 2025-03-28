package com.ExpenseTrackerApp.ExpenseTrackerApp.model.request;

import lombok.Getter;

@Getter
public class CategoryDto {
    private String name;
    private String description;

    public CategoryDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
