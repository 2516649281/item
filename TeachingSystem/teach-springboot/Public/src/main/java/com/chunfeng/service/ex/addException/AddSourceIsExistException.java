package com.chunfeng.service.ex.addException;

/**
 * 添加数据时数据已存在
 */
public class AddSourceIsExistException extends AddException {
    public AddSourceIsExistException() {
        super();
    }

    public AddSourceIsExistException(String message) {
        super(message);
    }

    public AddSourceIsExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddSourceIsExistException(Throwable cause) {
        super(cause);
    }

    protected AddSourceIsExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
