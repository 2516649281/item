package com.chunfeng.service.ex.logException;

/**
 * 修改数据时日志输出异常
 */
public class LogUpdateErrorException extends LogException {
    public LogUpdateErrorException() {
        super();
    }

    public LogUpdateErrorException(String message) {
        super(message);
    }

    public LogUpdateErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogUpdateErrorException(Throwable cause) {
        super(cause);
    }

    protected LogUpdateErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
