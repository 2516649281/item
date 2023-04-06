package com.chunfeng.service.ex.sourceException.selectException;

import com.chunfeng.service.ex.sourceException.SourceException;

/**
 * 查询异常
 */
public class SelectException extends SourceException {
    public SelectException() {
        super();
    }

    public SelectException(String message) {
        super(message);
    }

    public SelectException(String message, Throwable cause) {
        super(message, cause);
    }

    public SelectException(Throwable cause) {
        super(cause);
    }

    protected SelectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
