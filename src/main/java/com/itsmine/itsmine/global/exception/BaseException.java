package com.itsmine.itsmine.global.exception;

public abstract class BaseException extends RuntimeException {
    public abstract ErrorCode getErrorCode();
}
