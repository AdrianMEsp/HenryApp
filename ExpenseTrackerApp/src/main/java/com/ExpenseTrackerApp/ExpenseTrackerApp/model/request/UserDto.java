package com.ExpenseTrackerApp.ExpenseTrackerApp.model.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserDto {
    private String name;
    private String email;

    public UserDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}