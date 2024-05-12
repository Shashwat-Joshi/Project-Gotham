package com.auth.sessionandcookie.exception.exceptions.auth_service_exceptions;

import com.auth.sessionandcookie.constants.ExceptionConstants;
import com.auth.sessionandcookie.exception.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class AuthExUnauthorizedUserException extends BaseException {
    public AuthExUnauthorizedUserException() {
        super(HttpStatus.UNAUTHORIZED, ExceptionConstants.USER_UNAUTHORIZED_MSG);
    }
}