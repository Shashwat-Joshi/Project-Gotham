package com.auth.basicauthentication.service;

import com.auth.basicauthentication.dto.request.SignUpDTO;
import com.auth.basicauthentication.entity.UserCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PasswordHashingService {
    public UserCredentials hashPasswordWithSalt(SignUpDTO request) {

        // Generate Salt to avoid rainbow table mapping
        final String salt = BCrypt.gensalt();

        // Hash original password with salt
        final String hashedPass = BCrypt.hashpw(request.getPassword(), salt);

        log.info("Password Hashed Successfully");
        return UserCredentials.builder()
                .email(request.getEmail())
                .password(hashedPass)
                .build();
    }

    public boolean validatePassword(String password, UserCredentials userCredentials) {
        log.info("Validating Password for {}", userCredentials.getEmail());
        return BCrypt.checkpw(password, userCredentials.getPassword());
    }
}
