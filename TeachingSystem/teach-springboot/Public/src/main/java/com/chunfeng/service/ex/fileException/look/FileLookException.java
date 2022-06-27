package com.chunfeng.service.ex.fileException.look;

import com.chunfeng.service.ex.fileException.FileException;

/**
 * 文件查看异常
 */
public class FileLookException extends FileException {
    public FileLookException() {
        super();
    }

    public FileLookException(String s) {
        super(s);
    }

    public FileLookException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FileLookException(Throwable throwable) {
        super(throwable);
    }

    protected FileLookException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
