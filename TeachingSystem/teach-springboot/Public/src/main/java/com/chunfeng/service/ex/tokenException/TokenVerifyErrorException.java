package com.chunfeng.service.ex.tokenException;

/**
 * token验证错误
 *
 * @author 春风能解释
 * <p>
 * 2022/7/1
 */
public class TokenVerifyErrorException extends TokenException {
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
