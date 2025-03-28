package com.ExpenseTrackerApp.ExpenseTrackerApp.service.imple;

import com.ExpenseTrackerApp.ExpenseTrackerApp.model.User;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.request.UserDto;
import com.ExpenseTrackerApp.ExpenseTrackerApp.model.response.UserResponse;
import com.ExpenseTrackerApp.ExpenseTrackerApp.repository.UserRepository;
import com.ExpenseTrackerApp.ExpenseTrackerApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public void addUser(UserDto userDto) {
        User user = mapToUser(userDto);         //map tu User
        this.userRepository.save(user);  //implemented in CrudRepository
    }

    @Override
    public UserResponse getUserById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()){
            return mapToUserResponse(user.get());
        }
        return null;
    }

    @Override
    public List<UserResponse> getAllUsers(){
        return this.userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void updateUser(Long id, UserDto userDto) {
        User user = mapToUser(userDto);
        user.setId(id);
        this.userRepository.save(user);
    }

    /*
    Se modifico la clase delete User para devolver un boolean
    ya que devolvia codigo 200 en las prubas en insomnia por
    mas que el user no se encontrara
    */
    @Override
    public boolean deleteUser(Long id){
        if (this.userRepository.findById(id).isPresent()){
            this.userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private User mapToUser(UserDto userDto) {
        return new User (
                userDto.getName(),
                userDto.getEmail()
        );
    }

    private UserResponse mapToUserResponse(User user){
        return new UserResponse(user.getName(),
                user.getEmail());
    }
}
