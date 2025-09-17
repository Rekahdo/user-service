package com.rekahdo.user_service.repositories;

import com.rekahdo.user_service.entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    Optional<Phone> findByIdAndAppUserId(Long phoneId, Long userId);

    List<Phone> findAllByAppUserId(Long userId);

    @Modifying @Transactional
    void deleteByIdInAndAppUserId(List<Long> phoneId, Long userId);

    @Modifying @Transactional
    void deleteByIdAndAppUserId(Long phoneId, Long userId);

    @Modifying @Transactional
    void deleteByAppUserIdIn(List<Long> userIds);

    @Modifying @Transactional
    void deleteAllByAppUserId(Long userId);

    @Modifying @Transactional
    @Query("UPDATE Phone SET verified = true WHERE id = ?1 AND CONCAT(countryCode, number) = ?2")
    void verifyUserPhoneNumber(Long userId, String phoneNumber);

    boolean existsByAppUserIdAndCountryCodeAndNumber(Long userId, String countryCode, String number);
}
