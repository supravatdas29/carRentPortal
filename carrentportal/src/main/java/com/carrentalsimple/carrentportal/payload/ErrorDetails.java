package com.carrentalsimple.carrentportal.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@AllArgsConstructor
public class ErrorDetails {
    public String message;
    public Date date;
    public String description;
}
