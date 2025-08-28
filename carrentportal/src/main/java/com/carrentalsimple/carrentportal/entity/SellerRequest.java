package com.carrentalsimple.carrentportal.entity;

import com.carrentalsimple.carrentportal.entity.enums.SellerRequestStatus;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "seller_request")
public class SellerRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String carBrand;
    private String carModel;
    private int year;
    private double expectedPrice;
    private String carDetails;

    @Enumerated(EnumType.STRING)
    private SellerRequestStatus status = SellerRequestStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

}
