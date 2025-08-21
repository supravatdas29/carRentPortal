package com.carrentalsimple.carrentportal.controller;

import com.carrentalsimple.carrentportal.dto.CarRequestDto;
import com.carrentalsimple.carrentportal.dto.CarResponseDto;
import com.carrentalsimple.carrentportal.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    // ✅ Only Admin can add
    @PostMapping("/admin/{adminId}")
    public ResponseEntity<CarResponseDto> createCar(@PathVariable Long adminId,
                                                    @Valid @RequestBody CarRequestDto requestDto) {
        return ResponseEntity.ok(carService.createCar(adminId, requestDto));
    }

    // ✅ Only Admin can update
    @PutMapping("/admin/{adminId}/{carId}")
    public ResponseEntity<CarResponseDto> updateCar(@PathVariable Long adminId,
                                                    @PathVariable Long carId,
                                                    @Valid @RequestBody CarRequestDto requestDto) {
        return ResponseEntity.ok(carService.updateCar(adminId, carId, requestDto));
    }

    // ✅ Only Admin can delete
    @DeleteMapping("/admin/{adminId}/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long adminId, @PathVariable Long carId) {
        carService.deleteCar(adminId, carId);
        return ResponseEntity.noContent().build();
    }

    // ✅ Anyone can view all cars
    @GetMapping
    public ResponseEntity<List<CarResponseDto>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    // ✅ Anyone can view car details
    @GetMapping("/{carId}")
    public ResponseEntity<CarResponseDto> getCarById(@PathVariable Long carId) {
        return ResponseEntity.ok(carService.getCarById(carId));
    }
}
