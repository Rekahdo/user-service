package com.rekahdo.user_service.repositories;

import com.rekahdo.user_service.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Byte> {

    Optional<Authority> findByAppUserId(Long userId);

    @Modifying
    @Transactional
    void deleteByAppUserId(Long userId);

}
