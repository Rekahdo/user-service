package com.rekahdo.user_service.repositories;

import com.rekahdo.user_service.entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
}
