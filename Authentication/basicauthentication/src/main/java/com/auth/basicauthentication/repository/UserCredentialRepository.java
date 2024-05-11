package com.auth.basicauthentication.repository;

import com.auth.basicauthentication.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredentials, String> {
}
