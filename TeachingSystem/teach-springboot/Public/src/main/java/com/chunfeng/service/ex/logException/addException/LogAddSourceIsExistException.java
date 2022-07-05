package com.chunfeng.service.ex.logException.addException;

/**
 * 添加日志时数据已存在
 *
 * @author by 春风能解释
 * <p>
 * 2022/7/1
 */
public class LogAddSourceIsExistException extends LogAddException {
    public LogAddSourceIsExistException() {
        super();
    }

    public LogAddSourceIsExistException(String message) {
        super(message);
    }

    public LogAddSourceIsExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogAddSourceIsExistException(Throwable cause) {
        super(cause);
    }

    protected LogAddSourceIsExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
