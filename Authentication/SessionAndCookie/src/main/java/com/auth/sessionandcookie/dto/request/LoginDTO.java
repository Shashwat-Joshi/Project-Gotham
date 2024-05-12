package com.auth.sessionandcookie.dto.request;

import lombok.Getter;

@Getter
public class LoginDTO {
    private String email;
    private String password;
}
