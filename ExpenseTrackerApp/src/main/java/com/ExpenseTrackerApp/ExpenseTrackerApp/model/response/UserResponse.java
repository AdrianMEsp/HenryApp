package com.ExpenseTrackerApp.ExpenseTrackerApp.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserResponse {
    private String name;
    private String email;

    public UserResponse(String name, String email){
        this.name = name;
        this.email = email;
    }
}
