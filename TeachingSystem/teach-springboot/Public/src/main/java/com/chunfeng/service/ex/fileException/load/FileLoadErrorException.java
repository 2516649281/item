package com.chunfeng.service.ex.fileException.load;

/**
 * 文件上传失败异常
 */
public class FileLoadErrorException extends FileLoadException {
    public FileLoadErrorException() {
        super();
    }

    public FileLoadErrorException(String s) {
        super(s);
    }

    public FileLoadErrorException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FileLoadErrorException(Throwable throwable) {
        super(throwable);
    }

    protected FileLoadErrorException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
