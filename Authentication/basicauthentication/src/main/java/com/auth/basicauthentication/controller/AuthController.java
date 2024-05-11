package com.auth.basicauthentication.controller;

import com.auth.basicauthentication.dto.request.LoginDTO;
import com.auth.basicauthentication.dto.request.SignUpDTO;
import com.auth.basicauthentication.dto.response.ResponseDTO;
import com.auth.basicauthentication.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class AuthController {
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> signUp(@RequestBody SignUpDTO requestBody) {
        log.info("Received sign up request from: {}", requestBody.getEmail());
        return ResponseEntity.ok(authService.createUser(requestBody));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO requestBody) {
        log.info("Received log in request from: {}", requestBody.getEmail());
        return ResponseEntity.ok(authService.validateUserCredentials(requestBody));
    }
}
