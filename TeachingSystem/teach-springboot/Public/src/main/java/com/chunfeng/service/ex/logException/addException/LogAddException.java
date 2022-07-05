package com.chunfeng.service.ex.logException.addException;

import com.chunfeng.service.ex.logException.LogException;

/**
 * 添加日志异常超类
 *
 * @author by 春风能解释
 * <p>
 * 2022/7/1
 */
public class LogAddException extends LogException {
    public LogAddException() {
        super();
    }

    public LogAddException(String message) {
        super(message);
    }

    public LogAddException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogAddException(Throwable cause) {
        super(cause);
    }

    protected LogAddException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
