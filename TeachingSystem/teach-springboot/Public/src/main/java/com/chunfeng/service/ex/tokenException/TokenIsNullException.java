package com.chunfeng.service.ex.tokenException;

import com.chunfeng.service.ex.ServiceException;

/**
 * token为空
 */
public class TokenIsNullException extends ServiceException {
    public TokenIsNullException() {
        super();
    }

    public TokenIsNullException(String message) {
        super(message);
    }

    public TokenIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenIsNullException(Throwable cause) {
        super(cause);
    }

    protected TokenIsNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
