package com.carrentalsimple.carrentportal.controller;

import com.carrentalsimple.carrentportal.dto.CarRequestDto;
import com.carrentalsimple.carrentportal.dto.CarResponseDto;
import com.carrentalsimple.carrentportal.payload.APIResponse;
import com.carrentalsimple.carrentportal.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    // ✅ Only Admin can add
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<CarResponseDto>> addCar(@RequestBody CarRequestDto requestDto) {
        CarResponseDto carResponseDto = carService.addCar(requestDto);

        return ResponseEntity.ok(APIResponse.success("Car Added",carResponseDto));

    }



    // ✅ Only Admin can delete
    @DeleteMapping("deleteById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCar( @PathVariable Long id) {
        carService.removeCar(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Anyone can view all cars
    @GetMapping
    public ResponseEntity<List<CarResponseDto>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    // ✅ Anyone can view car details
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getById/{Id}")
    public ResponseEntity<CarResponseDto> getCarById(@PathVariable Long carId) {
        return ResponseEntity.ok(carService.getCarById(carId));
    }
}
