package com.chunfeng.service.ex.sourceException.updateException;

import com.chunfeng.service.ex.sourceException.SourceException;

/**
 * 修改异常
 */
public class UpdateException extends SourceException {
    public UpdateException() {
        super();
    }

    public UpdateException(String message) {
        super(message);
    }

    public UpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateException(Throwable cause) {
        super(cause);
    }

    protected UpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
