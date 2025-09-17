package com.rekahdo.user_service.repositories;

import com.rekahdo.user_service.entities.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findByOtp(Integer otp);

    Optional<Otp> findByAppUserId(Long userId);

    @Transactional @Modifying
    @Query("DELETE FROM Otp WHERE otp = ?1")
    void deleteByOtp(Integer otp);
}
