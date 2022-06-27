package com.chunfeng.service.ex.logException;

/**
 * 查询时日志输出异常
 */
public class LogSelectErrorException extends LogException {
    public LogSelectErrorException() {
        super();
    }

    public LogSelectErrorException(String message) {
        super(message);
    }

    public LogSelectErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogSelectErrorException(Throwable cause) {
        super(cause);
    }

    protected LogSelectErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
