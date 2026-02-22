package com._Rbrothers.user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com._Rbrothers.user_service.entity.RefreshToken;
import com._Rbrothers.user_service.entity.User;
 
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken>findByToken(String token);

    void deleteByUser(User user);
}