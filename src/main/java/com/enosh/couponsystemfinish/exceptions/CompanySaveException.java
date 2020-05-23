package com.enosh.couponsystemfinish.exceptions;

public class CompanySaveException extends Exception {

    public CompanySaveException() {
    }

    public CompanySaveException(String message) {
        super(message);
    }

    public CompanySaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompanySaveException(Throwable cause) {
        super(cause);
    }

    public CompanySaveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
