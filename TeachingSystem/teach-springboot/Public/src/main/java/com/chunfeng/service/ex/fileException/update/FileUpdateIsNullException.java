package com.chunfeng.service.ex.fileException.update;

/**
 * 文件修改时文件不存在
 */
public class FileUpdateIsNullException extends FileUpdateException {
    public FileUpdateIsNullException() {
        super();
    }

    public FileUpdateIsNullException(String s) {
        super(s);
    }

    public FileUpdateIsNullException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FileUpdateIsNullException(Throwable throwable) {
        super(throwable);
    }

    protected FileUpdateIsNullException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
