package com.chunfeng.service.ex.logException.updateException;

/**
 * 修改数据时数据为空
 *
 * @author by 春风能解释
 * <p>
 * 2022/7/1
 */
public class LogUpdateSourceIsNullException extends LogUpdateException {
    public LogUpdateSourceIsNullException() {
        super();
    }

    public LogUpdateSourceIsNullException(String message) {
        super(message);
    }

    public LogUpdateSourceIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogUpdateSourceIsNullException(Throwable cause) {
        super(cause);
    }

    protected LogUpdateSourceIsNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
