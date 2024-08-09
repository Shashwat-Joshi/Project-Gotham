package com.auth.jwtauthentication.service;

import com.auth.jwtauthentication.domain.dto.AuthenticationRequest;
import com.auth.jwtauthentication.domain.dto.AuthenticationResponse;
import com.auth.jwtauthentication.domain.dto.RegisterRequest;
import com.auth.jwtauthentication.domain.types.Role;
import com.auth.jwtauthentication.domain.types.TokenType;
import com.auth.jwtauthentication.entity.Token;
import com.auth.jwtauthentication.entity.User;
import com.auth.jwtauthentication.repository.TokenRepository;
import com.auth.jwtauthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(
            UserRepository userRepository,
            TokenRepository tokenRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        final User USER = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        final User SAVED_USER = userRepository.save(USER);
        final String JWT_TOKEN = jwtService.generateToken(SAVED_USER);

        final String SAVED_JWT_TOKEN = saveNewToken(SAVED_USER, JWT_TOKEN);

        return AuthenticationResponse.builder()
                .token(SAVED_JWT_TOKEN)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        final User SAVED_USER = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        final String JWT_TOKEN = jwtService.generateToken(SAVED_USER);

        final String SAVED_JWT_TOKEN = saveNewToken(SAVED_USER, JWT_TOKEN);

        return AuthenticationResponse.builder()
                .token(SAVED_JWT_TOKEN)
                .build();
    }

    private String saveNewToken(final User SAVED_USER, final String JWT_TOKEN) {
        Token token = Token.builder()
                .token(JWT_TOKEN)
                .user(SAVED_USER)
                .tokenType(TokenType.BEARER)
                .isRevoked(false)
                .isExpired(false)
                .build();

        // Invalidate old tokens
        tokenRepository.revokeOldTokens(SAVED_USER.getId());

        // Save new token
        final Token SAVED_TOKEN = tokenRepository.save(token);
        return SAVED_TOKEN.getToken();
    }
}
