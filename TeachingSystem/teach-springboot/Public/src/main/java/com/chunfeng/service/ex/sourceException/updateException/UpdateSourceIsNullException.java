package com.chunfeng.service.ex.sourceException.updateException;

/**
 * 删除时数据不存在
 */
public class UpdateSourceIsNullException extends UpdateException {
    public UpdateSourceIsNullException() {
        super();
    }

    public UpdateSourceIsNullException(String message) {
        super(message);
    }

    public UpdateSourceIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateSourceIsNullException(Throwable cause) {
        super(cause);
    }

    protected UpdateSourceIsNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
