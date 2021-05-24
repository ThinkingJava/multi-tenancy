package com.multi.tenant.exception;

public class VaildException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    public VaildException(String message) {
        super(message);
    }

    public VaildException(Throwable cause) {
        super(cause);
    }

    public VaildException(String message, Throwable cause) {
        super(message, cause);
    }

    public VaildException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
