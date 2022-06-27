package com.chunfeng.service.ex.logException;

import com.chunfeng.service.ex.ServiceException;

/**
 * 日志输出异常
 */
public class LogException extends ServiceException {
    public LogException() {
        super();
    }

    public LogException(String message) {
        super(message);
    }

    public LogException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogException(Throwable cause) {
        super(cause);
    }

    protected LogException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
