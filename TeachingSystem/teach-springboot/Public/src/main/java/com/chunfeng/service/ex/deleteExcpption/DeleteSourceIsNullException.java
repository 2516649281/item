package com.chunfeng.service.ex.deleteExcpption;

/**
 * 删除时数据不存在
 */
public class DeleteSourceIsNullException extends DeleteException {
    public DeleteSourceIsNullException() {
        super();
    }

    public DeleteSourceIsNullException(String message) {
        super(message);
    }

    public DeleteSourceIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteSourceIsNullException(Throwable cause) {
        super(cause);
    }

    protected DeleteSourceIsNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
