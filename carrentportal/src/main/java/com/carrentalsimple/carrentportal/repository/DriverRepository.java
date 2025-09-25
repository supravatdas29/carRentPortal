package com.carrentalsimple.carrentportal.repository;

import com.carrentalsimple.carrentportal.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver,Long> {
    
}
