package pl.marcinek.first_spring_boot_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.marcinek.first_spring_boot_app.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
