package com.auth.jwtauthentication.entity;

import com.auth.jwtauthentication.domain.types.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue // Default value is auto
    private Integer id;

    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private boolean isRevoked;

    private boolean isExpired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
