package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.BookingRequestDto;
import com.carrentalsimple.carrentportal.dto.BookingResponseDto;
import com.carrentalsimple.carrentportal.entity.Booking;
import com.carrentalsimple.carrentportal.entity.Car;
import com.carrentalsimple.carrentportal.entity.User;
import com.carrentalsimple.carrentportal.exception.ResourceNotFound;
import com.carrentalsimple.carrentportal.mapper.BookingMapper;
import com.carrentalsimple.carrentportal.repository.BookingRepository;
import com.carrentalsimple.carrentportal.repository.CarRepository;
import com.carrentalsimple.carrentportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;


    @Override
    public BookingResponseDto createBooking(Long userId, Long carId, BookingRequestDto bookingRequestDto) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        if (!car.isAvailable()) {
            throw new RuntimeException("Car is not available for booking");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("No Customer Found with id"));
        long days = ChronoUnit.DAYS.between(bookingRequestDto.getStartDate(), bookingRequestDto.getEndDate());
        if (days <= 0) {
            throw new RuntimeException("End date must be after start date");
        }

        double totalPrice = car.getPricePerDay() * days;

       Booking booking = BookingMapper.toEntity(user,car,bookingRequestDto);
       car.setAvailable(false);
       carRepository.save(car);

        Booking saved = bookingRepository.save(booking);

        // return response DTO
        return BookingMapper.toResponse(saved);



    }

    @Override
    public BookingResponseDto getBookingById(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Booking not found with id " + id));
        return BookingMapper.toResponse(booking);
    }

    @Override
    public List<BookingResponseDto> getBookingByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found with id " + userId));
        return bookingRepository.findByUser(user)
                .stream()
                .map(BookingMapper::toResponse)
                .toList();

    }
}
