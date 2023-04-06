package com.chunfeng.service.ex.sourceException.updateException;

/**
 * 修改数据失败
 *
 * @author by 春风能解释
 * <p>
 * 2022/7/1
 */
public class UpdateSourceErrorException extends UpdateException {
    public UpdateSourceErrorException() {
        super();
    }

    public UpdateSourceErrorException(String message) {
        super(message);
    }

    public UpdateSourceErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateSourceErrorException(Throwable cause) {
        super(cause);
    }

    protected UpdateSourceErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
