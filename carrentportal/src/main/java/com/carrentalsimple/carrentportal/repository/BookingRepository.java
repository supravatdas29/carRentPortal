package com.carrentalsimple.carrentportal.repository;

import com.carrentalsimple.carrentportal.entity.Booking;
import com.carrentalsimple.carrentportal.entity.Car;
import com.carrentalsimple.carrentportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByUser(User user);
    List<Booking> findByCar(Car carId);
}
