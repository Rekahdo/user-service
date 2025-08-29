package com.rekahdo.user_service.repositories;

import com.rekahdo.user_service.entities.AppUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

	boolean existsByUsername(String username);

    List<AppUser> findByEmail(String value);

    @Query("SELECT u FROM AppUser u WHERE CONCAT(u.phone.countryCode, u.phone.number) = ?1")
    List<AppUser> findByPhoneNumber(String phoneNumber);
}