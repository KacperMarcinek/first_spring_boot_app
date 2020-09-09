package pl.marcinek.first_spring_boot_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.marcinek.first_spring_boot_app.exception.ResourceNotFoundException;
import pl.marcinek.first_spring_boot_app.model.User;
import pl.marcinek.first_spring_boot_app.repository.UserRepository;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public Page<User> getAllUsers(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user){
        return userRepository.save(user);
    }

    @PutMapping("/users/{userId}")
    public User updateCarOwnerDetails(@PathVariable(value = "userId") Long userID,
                                            @Valid @RequestBody User userRequest){
        return userRepository.findById(userID).map(user -> {
            user.setName(userRequest.getName());
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("User Id " + userID + " not found."));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "userId") Long userId){
        return userRepository.findById(userId).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("User Id " + userId + " not found."));
    }
}
