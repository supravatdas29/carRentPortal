package com.carrentalsimple.carrentportal.entity;

import com.carrentalsimple.carrentportal.entity.enums.PaymentMethod;
import com.carrentalsimple.carrentportal.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    private String transactionId; // from payment gateway
    private String gatewayResponse;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime paidAt;



}
