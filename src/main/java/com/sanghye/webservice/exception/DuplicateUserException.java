package com.sanghye.webservice.exception;

public class DuplicateUserException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DuplicateUserException() {
        super();
    }

    public DuplicateUserException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DuplicateUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateUserException(String message) {
        super(message);
    }

    public DuplicateUserException(Throwable cause) {
        super(cause);
    }
}
