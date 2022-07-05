package com.chunfeng.service.ex.logException.updateException;

import com.chunfeng.service.ex.logException.LogException;

/**
 * 日志修改异常超类
 *
 * @author by 春风能解释
 * <p>
 * 2022/7/1
 */
public class LogUpdateException extends LogException {
    public LogUpdateException() {
        super();
    }

    public LogUpdateException(String message) {
        super(message);
    }

    public LogUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogUpdateException(Throwable cause) {
        super(cause);
    }

    protected LogUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
