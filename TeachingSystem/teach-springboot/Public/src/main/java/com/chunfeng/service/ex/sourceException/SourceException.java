package com.chunfeng.service.ex.sourceException;

import com.chunfeng.service.ex.ServiceException;

/**
 * 数据操作异常超类
 *
 * @author by 春风能解释
 * <p>
 * 2022/7/13
 */
public class SourceException extends ServiceException {
    public SourceException() {
        super();
    }

    public SourceException(String message) {
        super(message);
    }

    public SourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SourceException(Throwable cause) {
        super(cause);
    }

    protected SourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
