package com.carrentalsimple.carrentportal.repository;

import com.carrentalsimple.carrentportal.entity.PasswordResetToken;
import com.carrentalsimple.carrentportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByUser(User user);

    @Modifying
    @Query("DELETE FROM PasswordResetToken prt WHERE prt.user = ?1")
    void deleteByUser(User user);

    @Modifying
    @Query("DELETE FROM PasswordResetToken prt WHERE prt.expiryDate < ?1")
    void deleteExpiredTokens(Instant now);
}