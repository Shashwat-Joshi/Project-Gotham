package com.auth.basicauthentication.dto.response;

import com.auth.basicauthentication.exception.exceptions.BaseException;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ResponseDTO {
    private Object data;
    private String error;
    private int status;

    public static ResponseDTO generateSuccessResponse(Object data) {
        return ResponseDTO.builder()
                .data(data)
                .status(HttpStatus.OK.value())
                .build();
    }

    public static ResponseDTO generateFailureResponse(BaseException ex) {
        return ResponseDTO.builder()
                .error(ex.getErrorMsg())
                .status(ex.getHttpStatus().value())
                .build();
    }
}
