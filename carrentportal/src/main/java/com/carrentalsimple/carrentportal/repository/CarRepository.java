package com.carrentalsimple.carrentportal.repository;

import com.carrentalsimple.carrentportal.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    List<Car> findByAvailableTrue();
}
