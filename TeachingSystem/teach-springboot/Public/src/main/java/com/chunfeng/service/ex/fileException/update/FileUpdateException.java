package com.chunfeng.service.ex.fileException.update;

import com.chunfeng.service.ex.fileException.FileException;

/**
 * 文件修改异常超类
 */
public class FileUpdateException extends FileException {
    public FileUpdateException() {
        super();
    }

    public FileUpdateException(String s) {
        super(s);
    }

    public FileUpdateException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FileUpdateException(Throwable throwable) {
        super(throwable);
    }

    protected FileUpdateException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
