package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.CarRequestDto;
import com.carrentalsimple.carrentportal.dto.CarResponseDto;
import com.carrentalsimple.carrentportal.entity.Car;
import com.carrentalsimple.carrentportal.entity.User;
import com.carrentalsimple.carrentportal.entity.enums.UserRole;
import com.carrentalsimple.carrentportal.mapper.CarMapper;
import com.carrentalsimple.carrentportal.repository.CarRepository;
import com.carrentalsimple.carrentportal.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService{

    private final CarRepository carRepository;
    private final UserRepository userRepository;

    private void validateAdmin(Long adminId) {
        User user = userRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with ID: " + adminId));

        if (user.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("Access denied. Only Admins can perform this action.");
        }
    }
    @Override
    public CarResponseDto createCar(Long adminId, CarRequestDto requestDto) {
        validateAdmin(adminId);
        Car car = CarMapper.fromRequest(requestDto);
        return CarMapper.toResponse(carRepository.save(car));
    }

    @Override
    public CarResponseDto updateCar(Long adminId, Long carId, CarRequestDto requestDto) {
        validateAdmin(adminId);
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with ID: " + carId));
        car.setBrand(requestDto.getBrand());
        car.setModel(requestDto.getModel());
        car.setYear(requestDto.getYear());
        car.setPricePerDay(requestDto.getPricePerDay());
        return CarMapper.toResponse(carRepository.save(car));
    }

    @Override
    public void deleteCar(Long adminId, Long carId) {
        validateAdmin(adminId);
        if (!carRepository.existsById(carId)) {
            throw new EntityNotFoundException("Car not found with ID: " + carId);
        }
        carRepository.deleteById(carId);

    }

    @Override
    public List<CarResponseDto> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(CarMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CarResponseDto getCarById(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with ID: " + carId));
        return CarMapper.toResponse(car);
    }
}
