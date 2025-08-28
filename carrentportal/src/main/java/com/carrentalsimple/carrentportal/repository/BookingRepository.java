package com.carrentalsimple.carrentportal.repository;

import com.carrentalsimple.carrentportal.entity.Booking;
import com.carrentalsimple.carrentportal.entity.Car;
import com.carrentalsimple.carrentportal.entity.User;
import com.carrentalsimple.carrentportal.entity.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByUser(User user);
    List<Booking> findByCarId(Long carId);
    List<Booking> findByCarIdAndStatusInAndEndDateAfterAndStartDateBefore(
            Long carId,
            List<BookingStatus> statuses,
            LocalDate start,   // requested start
            LocalDate end      // requested end
    );
}
