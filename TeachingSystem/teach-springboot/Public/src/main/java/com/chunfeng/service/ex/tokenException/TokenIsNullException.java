package com.chunfeng.service.ex.tokenException;

/**
 * token为空
 *
 * @author 春风能解释
 * <p>
 * 2022/7/1
 */
public class TokenIsNullException extends TokenException {
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
