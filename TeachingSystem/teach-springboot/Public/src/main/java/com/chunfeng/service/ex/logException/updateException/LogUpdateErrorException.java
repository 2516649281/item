package com.chunfeng.service.ex.logException.updateException;

/**
 * 修改数据时日志输出异常
 *
 * @author 春风能解释
 * <p>
 * 2022/7/1
 */
public class LogUpdateErrorException extends LogUpdateException {
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
