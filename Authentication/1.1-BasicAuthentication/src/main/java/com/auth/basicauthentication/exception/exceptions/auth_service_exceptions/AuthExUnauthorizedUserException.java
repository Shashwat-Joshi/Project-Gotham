package com.auth.basicauthentication.exception.exceptions.auth_service_exceptions;

import com.auth.basicauthentication.constants.ExceptionConstants;
import com.auth.basicauthentication.exception.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class AuthExUnauthorizedUserException extends BaseException {
    public AuthExUnauthorizedUserException() {
        super(HttpStatus.UNAUTHORIZED, ExceptionConstants.USER_UNAUTHORIZED_MSG);
    }
}