package com.multi.tenant.exception;

public class TenantContextException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    public TenantContextException(String message) {
        super(message);
    }

    public TenantContextException(Throwable cause) {
        super(cause);
    }

    public TenantContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public TenantContextException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}