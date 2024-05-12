package com.auth.sessionandcookie.exception.exceptions.auth_service_exceptions;

import com.auth.sessionandcookie.constants.ExceptionConstants;
import com.auth.sessionandcookie.exception.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class AuthExUserExistsException extends BaseException {
    public AuthExUserExistsException() {
        super(HttpStatus.CONFLICT, ExceptionConstants.USER_EXISTS_MSG);
    }
}