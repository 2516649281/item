package com.chunfeng.service.ex.fileException.load;

/**
 * 文件上传时文件为空
 */
public class FileLoadIsNullException extends FileLoadException {
    public FileLoadIsNullException() {
        super();
    }

    public FileLoadIsNullException(String s) {
        super(s);
    }

    public FileLoadIsNullException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FileLoadIsNullException(Throwable throwable) {
        super(throwable);
    }

    protected FileLoadIsNullException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
