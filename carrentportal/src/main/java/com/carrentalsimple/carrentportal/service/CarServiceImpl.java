package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.CarRequestDto;
import com.carrentalsimple.carrentportal.dto.CarResponseDto;
import com.carrentalsimple.carrentportal.entity.Car;
import com.carrentalsimple.carrentportal.entity.User;
import com.carrentalsimple.carrentportal.entity.enums.Role;
import com.carrentalsimple.carrentportal.exception.ResourceNotFound;
import com.carrentalsimple.carrentportal.mapper.CarMapper;
import com.carrentalsimple.carrentportal.repository.CarRepository;
import com.carrentalsimple.carrentportal.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService{

    private final CarRepository carRepository;


    @Override
    public CarResponseDto addCar(CarRequestDto requestDto) {
        Car car = CarMapper.toEntity(requestDto);
        Car save = carRepository.save(car);
        return CarMapper.toResponse(save);
    }

    @Override
    public List<CarResponseDto> getAllCars() {
        return  carRepository.findAll().stream().map(CarMapper::toResponse).toList();
    }

    @Override
    public CarResponseDto getCarById(Long id) {
        Car byId = carRepository.findById(id).orElseThrow(() -> new ResourceNotFound("No such user found"+id));
        return CarMapper.toResponse(byId);
    }

    @Override
    public void removeCar(Long id) {
    if (!carRepository.existsById(id)){
        throw new IllegalArgumentException("No such id found");

        }

        carRepository.deleteById(id);
    }
}

