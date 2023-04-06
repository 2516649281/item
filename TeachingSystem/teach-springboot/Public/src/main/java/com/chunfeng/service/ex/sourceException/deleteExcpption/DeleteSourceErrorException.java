package com.chunfeng.service.ex.sourceException.deleteExcpption;

/**
 * 删除数据失败
 *
 * @author by 春风能解释
 * <p>
 * 2022/6/30
 */
public class DeleteSourceErrorException extends DeleteException {
    public DeleteSourceErrorException() {
        super();
    }

    public DeleteSourceErrorException(String message) {
        super(message);
    }

    public DeleteSourceErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteSourceErrorException(Throwable cause) {
        super(cause);
    }

    protected DeleteSourceErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
