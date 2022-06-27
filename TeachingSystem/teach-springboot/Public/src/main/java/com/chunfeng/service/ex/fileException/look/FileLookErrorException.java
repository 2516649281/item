package com.chunfeng.service.ex.fileException.look;

/**
 * 文件查看失败
 */
public class FileLookErrorException extends FileLookException {
    public FileLookErrorException() {
        super();
    }

    public FileLookErrorException(String s) {
        super(s);
    }

    public FileLookErrorException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FileLookErrorException(Throwable throwable) {
        super(throwable);
    }

    protected FileLookErrorException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
