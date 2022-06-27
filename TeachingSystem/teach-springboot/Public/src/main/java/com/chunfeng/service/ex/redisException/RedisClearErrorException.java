package com.chunfeng.service.ex.redisException;

/**
 * 缓存清理失败
 */
public class RedisClearErrorException extends RedisException {
    public RedisClearErrorException() {
        super();
    }

    public RedisClearErrorException(String message) {
        super(message);
    }

    public RedisClearErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisClearErrorException(Throwable cause) {
        super(cause);
    }

    protected RedisClearErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
