package com.carrentalsimple.carrentportal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;

    private String model;
    private String fuelType;

    private int year;

    private double pricePerDay;

    private boolean available = true;
    // optional: track whether driver is required
    private boolean withDriverAvailable = true;

    // optional: track whether self-drive is allowed
    private boolean selfDriveAvailable = true;
}
