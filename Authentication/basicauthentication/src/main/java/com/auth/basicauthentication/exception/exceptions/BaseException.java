package com.auth.basicauthentication.exception.exceptions;

import com.auth.basicauthentication.constants.ExceptionConstants;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String errorMsg;

    public BaseException(HttpStatus httpStatus, String msg) {
        this.httpStatus = httpStatus;
        this.errorMsg = msg;
    }

    public BaseException() {
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.errorMsg = ExceptionConstants.BASE_EXCEPTION_MSG;
    }
}
