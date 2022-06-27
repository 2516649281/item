package com.chunfeng.service.ex.fileException.load;

/**
 * 文件上传时检测到文件已存在
 */
public class FileLoadIsExistException extends FileLoadException {
    public FileLoadIsExistException() {
        super();
    }

    public FileLoadIsExistException(String s) {
        super(s);
    }

    public FileLoadIsExistException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FileLoadIsExistException(Throwable throwable) {
        super(throwable);
    }

    protected FileLoadIsExistException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
