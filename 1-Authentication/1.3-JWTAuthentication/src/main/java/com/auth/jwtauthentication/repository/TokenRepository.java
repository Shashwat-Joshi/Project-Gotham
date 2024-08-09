package com.auth.jwtauthentication.repository;

import com.auth.jwtauthentication.entity.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Transactional
public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findByToken(String token);

    @Modifying
    @Query("update Token set isRevoked = true where isRevoked = false and user.id = :userId")
    void revokeOldTokens(Integer userId);
}
