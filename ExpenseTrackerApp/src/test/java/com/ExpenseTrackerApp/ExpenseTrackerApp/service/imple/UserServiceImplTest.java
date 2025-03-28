package com.ExpenseTrackerApp.ExpenseTrackerApp.service.imple;

import com.ExpenseTrackerApp.ExpenseTrackerApp.model.User;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.request.UserDto;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.response.UserResponse;
import com.ExpenseTrackerApp.ExpenseTrackerApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        user = new User("Adrian","unmail@gmail.com");
        userDto = new UserDto("Adrian","unsegundomail@gmail.com");
    }

    @Test
    @DisplayName("Test add a new user")
    void testAddUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.addUser(userDto);

        verify(userRepository,times(1))
                .save(any(User.class));
    }

    @Test
    @DisplayName("Test get all users")
    void testGetAllUsers() {
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);

        List<UserResponse> responses = userService.getAllUsers();

        assertNotNull(responses);
        assertEquals(1,responses.size());
    }

    @Test
    @DisplayName("Test update an existing user")
    void testUpdateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.updateUser(1L,userDto);

        verify(userRepository,times(1))
                .save(any(User.class));
    }

    @Test
    @DisplayName("Test delete an existing user")
    void testDeleteUser() {
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository,times(1))
                .deleteById(1L);
    }

}