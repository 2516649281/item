package com.chunfeng.service.ex.fileException.delete;

import com.chunfeng.service.ex.fileException.FileException;

/**
 * 文件删除异常超类
 */
public class FileDeleteException extends FileException {
    public FileDeleteException() {
        super();
    }

    public FileDeleteException(String s) {
        super(s);
    }

    public FileDeleteException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FileDeleteException(Throwable throwable) {
        super(throwable);
    }

    protected FileDeleteException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
