package com.auth.sessionandcookie.service;

import com.auth.sessionandcookie.dto.request.LoginDTO;
import com.auth.sessionandcookie.dto.request.SignUpDTO;
import com.auth.sessionandcookie.dto.response.ResponseDTO;
import com.auth.sessionandcookie.entity.UserCredentials;
import com.auth.sessionandcookie.exception.exceptions.auth_service_exceptions.AuthExUnauthorizedUserException;
import com.auth.sessionandcookie.exception.exceptions.auth_service_exceptions.AuthExUserExistsException;
import com.auth.sessionandcookie.exception.exceptions.auth_service_exceptions.AuthExUserNotFoundException;
import com.auth.sessionandcookie.repository.UserCredentialRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class AuthService {
    private UserCredentialRepository repository;
    private PasswordHashingService passwordHashingService;

    // createUser - Create user if user doesn't exist
    public ResponseDTO createUser(SignUpDTO requestBody) {
        Optional<UserCredentials> user = repository.findById(requestBody.getEmail());

        if (user.isPresent()) throw new AuthExUserExistsException();

        final UserCredentials USER_CREDENTIALS = passwordHashingService.hashPasswordWithSalt(requestBody);
        repository.save(USER_CREDENTIALS);
        return ResponseDTO.generateSuccessResponse(Map.of("email", USER_CREDENTIALS.getEmail()));
    }

    // validateUserCredentials - Validate user if present in DB
    public ResponseDTO validateUserCredentials(LoginDTO requestBody) {
        Optional<UserCredentials> user = repository.findById(requestBody.getEmail());

        if (user.isEmpty()) throw new AuthExUserNotFoundException();

        final boolean IS_AUTHENTICATED = passwordHashingService.validatePassword(requestBody.getPassword(), user.get());
        if (!IS_AUTHENTICATED) throw new AuthExUnauthorizedUserException();
        return ResponseDTO.generateSuccessResponse(Map.of("isAuthenticated", true));
    }
}
