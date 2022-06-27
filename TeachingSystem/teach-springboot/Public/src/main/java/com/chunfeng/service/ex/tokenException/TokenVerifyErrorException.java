package com.chunfeng.service.ex.tokenException;

import com.chunfeng.service.ex.ServiceException;

/**
 * token验证错误
 */
public class TokenVerifyErrorException extends ServiceException {
    public TokenVerifyErrorException() {
        super();
    }

    public TokenVerifyErrorException(String message) {
        super(message);
    }

    public TokenVerifyErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenVerifyErrorException(Throwable cause) {
        super(cause);
    }

    protected TokenVerifyErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
