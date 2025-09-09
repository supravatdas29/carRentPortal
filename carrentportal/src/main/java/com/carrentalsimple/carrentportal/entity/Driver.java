package com.carrentalsimple.carrentportal.entity;


import com.carrentalsimple.carrentportal.entity.enums.DriverStatus;
import jakarta.persistence.*;
import lombok.*;

@Table(name="drivers")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(unique = true,nullable = false)
    private String licenseNumber;


    private String phone;

    private DriverStatus driverStatus;

    @OneToOne
    private User user;
}
