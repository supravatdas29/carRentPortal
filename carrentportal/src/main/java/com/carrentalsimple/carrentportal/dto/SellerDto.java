package com.carrentalsimple.carrentportal.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private List<SellerRequestDto> requests;
}
