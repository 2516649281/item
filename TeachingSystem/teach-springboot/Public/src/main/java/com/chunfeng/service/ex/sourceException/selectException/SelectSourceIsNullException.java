package com.chunfeng.service.ex.sourceException.selectException;

/**
 * 查询的数据不存在
 */
public class SelectSourceIsNullException extends SelectException {
    public SelectSourceIsNullException() {
        super();
    }

    public SelectSourceIsNullException(String message) {
        super(message);
    }

    public SelectSourceIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public SelectSourceIsNullException(Throwable cause) {
        super(cause);
    }

    protected SelectSourceIsNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
