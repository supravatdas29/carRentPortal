package com.carrentalsimple.carrentportal.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerCreateDto {
    private String name;
    private String email;
    private String phone;
}
