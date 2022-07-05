package com.chunfeng.service.ex.logException.selectException;

import com.chunfeng.service.ex.logException.LogException;

/**
 * 查询日志超类
 *
 * @author by 春风能解释
 * <p>
 * 2022/7/1
 */
public class LogSelectException extends LogException {
    public LogSelectException() {
        super();
    }

    public LogSelectException(String message) {
        super(message);
    }

    public LogSelectException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogSelectException(Throwable cause) {
        super(cause);
    }

    protected LogSelectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
