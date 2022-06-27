package com.chunfeng.service.ex.fileException.update;

/**
 * 文件修改失败异常
 */
public class FileUpdateErrorException extends FileUpdateException {
    public FileUpdateErrorException() {
        super();
    }

    public FileUpdateErrorException(String s) {
        super(s);
    }

    public FileUpdateErrorException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FileUpdateErrorException(Throwable throwable) {
        super(throwable);
    }

    protected FileUpdateErrorException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
