package com.itsmine.itsmine.global.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final String code;
    private final String message;
    private final int status;
    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.getCode(),
                errorCode.getMessage(),
                errorCode.getStatus().value()
        );
    }
}
