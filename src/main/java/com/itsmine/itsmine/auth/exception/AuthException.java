package com.itsmine.itsmine.auth.exception;

import com.itsmine.itsmine.global.exception.BaseException;

public class AuthException extends BaseException {

    private final AuthErrorCode errorCode;

    public AuthException(AuthErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    @Override
    public AuthErrorCode getErrorCode() {
        return this.errorCode;
    }

}
