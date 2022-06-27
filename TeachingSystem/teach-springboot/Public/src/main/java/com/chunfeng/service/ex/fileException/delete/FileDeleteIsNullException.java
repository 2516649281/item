package com.chunfeng.service.ex.fileException.delete;

/**
 * 文件删除时文件不存在
 */
public class FileDeleteIsNullException extends FileDeleteException {
    public FileDeleteIsNullException() {
        super();
    }

    public FileDeleteIsNullException(String s) {
        super(s);
    }

    public FileDeleteIsNullException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FileDeleteIsNullException(Throwable throwable) {
        super(throwable);
    }

    protected FileDeleteIsNullException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
