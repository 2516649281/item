package com.chunfeng.service.ex.logException.deleteException;

import com.chunfeng.service.ex.logException.LogException;

/**
 * 日志删除异常超类
 *
 * @author by 春风能解释
 * <p>
 * 2022/7/1
 */
public class LogDeleteException extends LogException {
    public LogDeleteException() {
        super();
    }

    public LogDeleteException(String message) {
        super(message);
    }

    public LogDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogDeleteException(Throwable cause) {
        super(cause);
    }

    protected LogDeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
