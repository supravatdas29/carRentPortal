package com.carrentalsimple.carrentportal.repository;

import com.carrentalsimple.carrentportal.entity.Booking;
import com.carrentalsimple.carrentportal.entity.User;
import com.carrentalsimple.carrentportal.entity.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    List<Booking> findByCustomer(User customer);

    List<Booking> findByCustomerId(Long customerId);

    List<Booking> findByCarId(Long carId);

    List<Booking> findByCarIdAndStatusInAndEndDateAfterAndStartDateBefore(
            Long carId,
            List<BookingStatus> statuses,
            LocalDate start,   // requested start
            LocalDate end      // requested end
    );

    @Query("""
        SELECT b FROM Booking b
        WHERE b.car.id = :carId
        AND b.status IN ('CONFIRMED','PENDING')
        AND b.endDate >= :startDate
        AND b.startDate <= :endDate
    """)
    List<Booking> findOverlappingBookings(Long carId, LocalDate startDate, LocalDate endDate);
}
