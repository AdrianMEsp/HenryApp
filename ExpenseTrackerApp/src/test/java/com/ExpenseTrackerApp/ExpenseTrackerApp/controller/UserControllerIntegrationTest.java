package com.ExpenseTrackerApp.ExpenseTrackerApp.controller;

import com.ExpenseTrackerApp.ExpenseTrackerApp.model.User;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.request.UserDto;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.response.UserResponse;
import com.ExpenseTrackerApp.ExpenseTrackerApp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;
    private UserResponse user;

    @BeforeEach
    void setUp() {
        user = new UserResponse("Andrew Garfield","spiderman@gmail.com");
    }

    @Test
    void getAllUsers() throws Exception {
        List<UserResponse> responseMock = List.of(user);

        when(userService.getAllUsers()).thenReturn(responseMock);

        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lenght()").value(1));

        verify(userService,times(1));
    }

    @Test
    void getUserById() throws Exception{
    }

    @Test
    void addUser() throws Exception{
    }

    @Test
    void updateUser() throws Exception{
    }

    @Test
    void deleteUser() throws Exception{
    }
}