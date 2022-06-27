package com.chunfeng.service.ex.fileException.look;

/**
 * 文件查看时文件不存在异常
 */
public class FileLookIsNullException extends FileLookException {
    public FileLookIsNullException() {
        super();
    }

    public FileLookIsNullException(String s) {
        super(s);
    }

    public FileLookIsNullException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FileLookIsNullException(Throwable throwable) {
        super(throwable);
    }

    protected FileLookIsNullException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
