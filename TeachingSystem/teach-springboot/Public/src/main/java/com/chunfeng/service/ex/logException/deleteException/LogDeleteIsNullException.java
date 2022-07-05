package com.chunfeng.service.ex.logException.deleteException;

/**
 * 删除日志时日志为空
 *
 * @author by 春风能解释
 * <p>
 * 2022/7/1
 */
public class LogDeleteIsNullException extends LogDeleteException {
    public LogDeleteIsNullException() {
        super();
    }

    public LogDeleteIsNullException(String message) {
        super(message);
    }

    public LogDeleteIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogDeleteIsNullException(Throwable cause) {
        super(cause);
    }

    protected LogDeleteIsNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
