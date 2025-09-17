package com.rekahdo.user_service.repositories;

import com.rekahdo.user_service.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsernameIgnoreCase(String username);

	boolean existsByUsernameIgnoreCase(String username);

    List<AppUser> findByEmail(String value);

    @Query("SELECT u FROM AppUser u JOIN u.phones p WHERE u.id = ?1 AND CONCAT(p.countryCode, p.number) = ?2")
    Optional<AppUser> findByIdAndPhoneNumber(Long userId, String phoneNumber);

    List<AppUser> findAllByPhones_CountryCodeAndPhones_Number(String countryCode, String number);

    Optional<AppUser> findByIdAndEmail(Long userId, String email);

    @Modifying @Transactional
    @Query("UPDATE AppUser SET verified = true WHERE id = ?1")
    void verifyUserEmail(Long userId);

    @Modifying @Transactional
    @Query("UPDATE AppUser SET password = ?1 WHERE id = ?2")
    void updatePassword(String password, Long userId);
}