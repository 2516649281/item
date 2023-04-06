package com.chunfeng.service.ex.sourceException.selectException;

/**
 * 查询的数据已被删除
 */
public class SelectSourceIsDeletedException extends SelectException {
    public SelectSourceIsDeletedException() {
        super();
    }

    public SelectSourceIsDeletedException(String message) {
        super(message);
    }

    public SelectSourceIsDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SelectSourceIsDeletedException(Throwable cause) {
        super(cause);
    }

    protected SelectSourceIsDeletedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
