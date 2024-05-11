package com.auth.basicauthentication.dto.request;

import lombok.Getter;

@Getter
public class LoginDTO {
    private String email;
    private String password;
}
