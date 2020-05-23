package com.enosh.couponsystemfinish.exceptions;

public class LoggedOutException extends Exception {

    public LoggedOutException() {
    }

    public LoggedOutException(String message) {
        super(message);
    }

    public LoggedOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoggedOutException(Throwable cause) {
        super(cause);
    }

    public LoggedOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
