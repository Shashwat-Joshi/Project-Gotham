package com.auth.jwtauthentication.service;

import com.auth.jwtauthentication.entity.Token;
import com.auth.jwtauthentication.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Autowired
    public LogoutService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String AUTH_HEADER = request.getHeader("Authorization");
        final String USER_SENT_JWT, USER_EMAIL;

        if(null == AUTH_HEADER || !AUTH_HEADER.startsWith("Bearer ")) {
            return;
        }

        USER_SENT_JWT = AUTH_HEADER.substring(7);

        Optional<Token> SAVED_TOKEN = tokenRepository.findByToken(USER_SENT_JWT);
        if(SAVED_TOKEN.isPresent()) {
            SAVED_TOKEN.get().setExpired(true);
            SAVED_TOKEN.get().setRevoked(true);
            tokenRepository.save(SAVED_TOKEN.get());
        }
    }
}
