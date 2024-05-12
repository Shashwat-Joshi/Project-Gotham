package com.auth.sessionandcookie.repository;

import com.auth.sessionandcookie.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredentials, String> {
}
