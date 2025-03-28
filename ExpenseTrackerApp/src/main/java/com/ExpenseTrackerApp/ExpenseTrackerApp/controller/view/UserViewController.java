package com.ExpenseTrackerApp.ExpenseTrackerApp.controller.view;

import com.ExpenseTrackerApp.ExpenseTrackerApp.model.request.UserDto;
import com.ExpenseTrackerApp.ExpenseTrackerApp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserViewController {

    private final UserService userService;

    public UserViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users-view")
    public String showUserPage(Model model) {
        model.addAttribute("users",
                this.userService.getAllUsers());
        return "users";
    }

    @PostMapping("/add-user")
    public String addUser(
            @RequestParam String name,
            @RequestParam String email,
            Model model) {

        UserDto user = new UserDto(name, email);
        userService.addUser(user);
        model.addAttribute("message", "User added successfully");
        return "users";
    }
}
