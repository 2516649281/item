package com.chunfeng.service.ex.logException;

/**
 * 添加数据时日志输出异常
 */
public class LogAddErrorException extends LogException {
    public LogAddErrorException() {
        super();
    }

    public LogAddErrorException(String message) {
        super(message);
    }

    public LogAddErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogAddErrorException(Throwable cause) {
        super(cause);
    }

    protected LogAddErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
