package pl.marcinek.first_spring_boot_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.marcinek.first_spring_boot_app.exception.ResourceNotFoundException;
import pl.marcinek.first_spring_boot_app.model.Car;
import pl.marcinek.first_spring_boot_app.repository.CarRepository;

import javax.validation.Valid;

@RestController
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/cars")
    public Page<Car> getAllCars(Pageable pageable){
        return carRepository.findAll(pageable);
    }

    @PostMapping("/cars")
    public Car addCar(@Valid @RequestBody Car car){
        return carRepository.save(car);
    }

    @PutMapping("/cars/{carId}")
    public Car updateCarDetails(@PathVariable(value = "carId") Long carId,
                                    @Valid @RequestBody Car carRequest){
        return carRepository.findById(carId).map(car -> {
            car.setCarModel(carRequest.getCarModel());
            return carRepository.save(car);
        }).orElseThrow(() -> new ResourceNotFoundException("car Id " + carId + " not found."));
    }

    @DeleteMapping("car/{carId}")
    public ResponseEntity<?> deleteCar(@PathVariable(value = "carId") Long carId){
        return carRepository.findById(carId).map(car -> {
            carRepository.delete(car);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("car Id " + carId + " not found."));
    }

}
