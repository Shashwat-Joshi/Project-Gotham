package com.auth.basicauthentication.exception.exceptions.auth_service_exceptions;

import com.auth.basicauthentication.constants.ExceptionConstants;
import com.auth.basicauthentication.exception.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class AuthExUserNotFoundException extends BaseException {
    public AuthExUserNotFoundException() {
        super(HttpStatus.NOT_FOUND, ExceptionConstants.USER_NOT_FOUND_MSG);
    }
}