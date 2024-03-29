package com.chunfeng.service.ex.sourceException.addException;

import com.chunfeng.service.ex.sourceException.SourceException;

/**
 * 添加异常
 */
public class AddException extends SourceException {
    public AddException() {
        super();
    }

    public AddException(String message) {
        super(message);
    }

    public AddException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddException(Throwable cause) {
        super(cause);
    }

    protected AddException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
