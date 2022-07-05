package com.chunfeng.service.ex.addException;

/**
 * 添加数据失败
 *
 * @author by 春风能解释
 * <p>
 * 2022/7/1
 */
public class AddSourceErrorException extends AddException {
    public AddSourceErrorException() {
        super();
    }

    public AddSourceErrorException(String message) {
        super(message);
    }

    public AddSourceErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddSourceErrorException(Throwable cause) {
        super(cause);
    }

    protected AddSourceErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
