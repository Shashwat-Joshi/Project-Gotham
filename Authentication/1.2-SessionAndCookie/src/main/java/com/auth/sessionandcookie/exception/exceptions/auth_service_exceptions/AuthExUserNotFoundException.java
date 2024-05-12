package com.auth.sessionandcookie.exception.exceptions.auth_service_exceptions;

import com.auth.sessionandcookie.constants.ExceptionConstants;
import com.auth.sessionandcookie.exception.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class AuthExUserNotFoundException extends BaseException {
    public AuthExUserNotFoundException() {
        super(HttpStatus.NOT_FOUND, ExceptionConstants.USER_NOT_FOUND_MSG);
    }
}