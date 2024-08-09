package com.auth.jwtauthentication.service;

import com.auth.jwtauthentication.entity.Token;
import com.auth.jwtauthentication.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    private final TokenRepository tokenRepository;

    @Autowired
    public JwtService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    private final static String SECRET_KEY = "E7628CFC8C8284863424D47E983F84F749E7635A59A16BC5426662D118";

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValid(final String TOKEN, final UserDetails USER) {
        Optional<Token> SAVED_TOKEN = tokenRepository.findByToken(TOKEN);
        if(SAVED_TOKEN.isEmpty()) return false;

        final String TOKEN_FROM_DB = SAVED_TOKEN.get().getToken();

        // Validate Username
        final String USERNAME = extractUsername(TOKEN_FROM_DB);
        if(!USERNAME.equals(USER.getUsername())) return false;

        // Validate Token Expiry
        final Date CURRENT_TIME = new Date(System.currentTimeMillis());
        final Date EXPIRY_TIME = extractClaims(TOKEN_FROM_DB, Claims::getExpiration);
        if(EXPIRY_TIME.before(CURRENT_TIME)) {
            // update DB then return false
            SAVED_TOKEN.get().setExpired(true);
            tokenRepository.save(SAVED_TOKEN.get());
            return false;
        };

        // Check for revoked or expired tokens
        if(SAVED_TOKEN.get().isExpired() || SAVED_TOKEN.get().isRevoked()) return false;

        return true;
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims CLAIMS = extractAllClaims(token);
        return claimsResolver.apply(CLAIMS);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] bytes = Base64.getDecoder()
                .decode(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Keys.hmacShaKeyFor(bytes);
    }
}