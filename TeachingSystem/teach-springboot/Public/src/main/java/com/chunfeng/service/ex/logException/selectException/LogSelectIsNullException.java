package com.chunfeng.service.ex.logException.selectException;

/**
 * 查询日志时数据为空
 *
 * @author by 春风能解释
 * <p>
 * 2022/7/1
 */
public class LogSelectIsNullException extends LogSelectException {
    public LogSelectIsNullException() {
        super();
    }

    public LogSelectIsNullException(String message) {
        super(message);
    }

    public LogSelectIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogSelectIsNullException(Throwable cause) {
        super(cause);
    }

    protected LogSelectIsNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
