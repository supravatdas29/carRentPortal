package com.carrentalsimple.carrentportal.entity;

import com.carrentalsimple.carrentportal.entity.enums.RequestStatus;
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


    private String carDetails;

    private RequestStatus status = RequestStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
}
