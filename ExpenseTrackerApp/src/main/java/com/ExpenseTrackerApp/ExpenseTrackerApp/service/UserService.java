package com.ExpenseTrackerApp.ExpenseTrackerApp.service;

import com.ExpenseTrackerApp.ExpenseTrackerApp.model.request.UserDto;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.response.UserResponse;
import org.springframework.stereotype.Service;
import java.util.List;


public interface UserService {
    void addUser(UserDto userDto);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    void updateUser(Long id, UserDto userDto);

    boolean deleteUser(Long id);
}
