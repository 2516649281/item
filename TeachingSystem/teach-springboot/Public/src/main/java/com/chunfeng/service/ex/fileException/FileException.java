package com.chunfeng.service.ex.fileException;

import com.chunfeng.service.ex.ServiceException;

/**
 * 文件系异常超类
 */
public class FileException extends ServiceException {
    public FileException() {
        super();
    }

    public FileException(String s) {
        super(s);
    }

    public FileException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FileException(Throwable throwable) {
        super(throwable);
    }

    protected FileException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
