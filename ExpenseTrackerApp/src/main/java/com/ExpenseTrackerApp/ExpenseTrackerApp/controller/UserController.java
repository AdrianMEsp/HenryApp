package com.ExpenseTrackerApp.ExpenseTrackerApp.controller;

import com.ExpenseTrackerApp.ExpenseTrackerApp.exception.UserNotFoundException;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.request.UserDto;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.response.UserResponse;
import com.ExpenseTrackerApp.ExpenseTrackerApp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @GetMapping
    public List<UserResponse> getAllUsers(){
        log.info("Obtaining all users");
        try {
            return userService.getAllUsers();
        } catch (Exception e) {
            log.error("Error obtaining all users", e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id){
        log.info("Obtaining user with id: {}", id);
        try {
            UserResponse user = userService.getUserById(id);
            if (user == null){
                throw new UserNotFoundException(id);
            }
            return user;
        } catch(UserNotFoundException e){
            log.error("Bad ID Request",e);
            throw e;
        }
    }

    @PostMapping("/add")
    public void addUser(@RequestBody UserDto userDto){
        log.info("Adding user with description: {}", userDto.toString());
        try {
            userService.addUser(userDto);
            log.info("User added successfully");
        } catch (Exception e) {
            log.error("Error adding user: {}", userDto.toString(), e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id,
                           @RequestBody UserDto userDto){
        log.info("Modifying user with id: {}", id);
        try {
            this.userService.updateUser(id, userDto);
            log.info("User with id {} modified successfully", id);
        } catch (Exception e) {
            log.error("Error modifying user with id: {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        log.info("Deleting user with id: {}", id);
        try {
            boolean deleted = this.userService.deleteUser(id);
            if (!deleted) {
                throw new UserNotFoundException(id);
            }
            log.info("User with id {} deleted successfully", id);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error deleting user with id: {}", id, e);
            throw e;
        }
    }
}
