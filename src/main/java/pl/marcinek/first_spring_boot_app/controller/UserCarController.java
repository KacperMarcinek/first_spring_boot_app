package pl.marcinek.first_spring_boot_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.marcinek.first_spring_boot_app.exception.ResourceNotFoundException;
import pl.marcinek.first_spring_boot_app.model.User;
import pl.marcinek.first_spring_boot_app.repository.CarRepository;
import pl.marcinek.first_spring_boot_app.repository.UserRepository;

@RestController
public class UserCarController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    @PutMapping("users/{userId}/cars/{carId}")
    public User createLink(@PathVariable(value = "userId") Long userId,
                                @PathVariable(value = "carId") Long carId){
        if (!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("User Id " + userId + " not found.");
        }else if (!carRepository.existsById(carId)){
            throw new ResourceNotFoundException("Car Id " + carId + " not found.");
        }
        return userRepository.findById(userId).map(user -> {
            user.getCars().add(carRepository.findById(carId).get());
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("User Id " + userId + " not found."));
    }

    @DeleteMapping("/users/{userId}/cars/{carId}")
    public User deleteCar(@PathVariable(value = "userId") Long userId,
                                @PathVariable(value = "carId") Long carId){
        if (!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("User Id " + userId + " not found.");
        }else if (!carRepository.existsById(carId)){
            throw new ResourceNotFoundException("Car Id " + carId + " not found.");
        }
        return userRepository.findById(userId).map(user -> {
            user.getCars().remove(carRepository.getOne(carId));
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("User Id " + userId + " not found."));
    }
}

