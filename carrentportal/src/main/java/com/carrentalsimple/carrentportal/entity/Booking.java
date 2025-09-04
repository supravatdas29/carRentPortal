package com.carrentalsimple.carrentportal.entity;

import com.carrentalsimple.carrentportal.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    private LocalDate startDate;
    private LocalDate endDate;

    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private BookingStatus status ;
}
