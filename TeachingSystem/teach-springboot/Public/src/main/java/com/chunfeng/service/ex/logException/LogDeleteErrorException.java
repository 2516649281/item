package com.chunfeng.service.ex.logException;

/**
 * 删除数据时日志输出异常
 */
public class LogDeleteErrorException extends LogException {
    public LogDeleteErrorException() {
        super();
    }

    public LogDeleteErrorException(String message) {
        super(message);
    }

    public LogDeleteErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogDeleteErrorException(Throwable cause) {
        super(cause);
    }

    protected LogDeleteErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
