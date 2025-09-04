package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.BookingRequestDto;
import com.carrentalsimple.carrentportal.dto.BookingResponseDto;
import com.carrentalsimple.carrentportal.entity.Booking;
import com.carrentalsimple.carrentportal.entity.Car;
import com.carrentalsimple.carrentportal.entity.User;
import com.carrentalsimple.carrentportal.entity.enums.BookingStatus;
import com.carrentalsimple.carrentportal.exception.ResourceNotFound;
import com.carrentalsimple.carrentportal.mapper.BookingMapper;
import com.carrentalsimple.carrentportal.repository.BookingRepository;
import com.carrentalsimple.carrentportal.repository.CarRepository;
import com.carrentalsimple.carrentportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    @Override
    public BookingResponseDto createBooking(BookingRequestDto request) {
        Car car = carRepository.findById(request.getCarId()).
                orElseThrow(() -> new ResourceNotFound("No Such Car Found"+request.getCarId()));
        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFound("User not found with ID " + request.getCustomerId()));

        boolean available = bookingRepository.findOverlappingBookings(car.getId(),request.getStartDate(),request.getEndDate()).isEmpty();

        if(!available){
            throw new IllegalStateException("Car is Not available for Booking for selected dates.");

        }

        long days = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate()) + 1;// inclusive
        if(days <= 0){
            throw new RuntimeException("End date must be after start date");
        }
        double totalPrice = days * car.getPricePerDay();

        Booking booking = BookingMapper.toEntity(customer,car,request);
        car.setAvailable(false);
        booking.setStatus(BookingStatus.CONFIRMED);
        carRepository.save(car);

        Booking saved = bookingRepository.save(booking);

        return BookingMapper.toResponse(saved);

    }

    @Override
    public BookingResponseDto getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFound("No such User found"));
         return BookingMapper.toResponse(booking);
    }

    @Override
    public List<BookingResponseDto> getBookingByUser(Long customerId) {
        return bookingRepository.findByCustomerId(customerId).stream()
                .map(BookingMapper::toResponse).toList();
    }

    @Override
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFound("No such User found"));
        booking.setStatus(BookingStatus.CANCELLED);
        Car car = booking.getCar();
        car.setAvailable(true);
        carRepository.save(car);

         bookingRepository.save(booking);

    }

    @Override
    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(BookingMapper::toResponse)
                .toList();
    }
}
