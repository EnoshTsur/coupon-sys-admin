package com.enosh.couponsystemfinish.exceptions;

public class DoesntExistsException extends Exception{
    public DoesntExistsException() {
    }

    public DoesntExistsException(String message) {
        super(message);
    }

    public DoesntExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DoesntExistsException(Throwable cause) {
        super(cause);
    }

    public DoesntExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
