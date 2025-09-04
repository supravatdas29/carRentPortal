package com.carrentalsimple.carrentportal.repository;

import com.carrentalsimple.carrentportal.entity.User;
import com.carrentalsimple.carrentportal.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByRole(Role role);
    Optional<User> findByEmail(String email);
}
