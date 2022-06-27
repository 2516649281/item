package com.chunfeng.service.ex.fileException.delete;

/**
 * 文件删除失败
 */
public class FileDeleteErrorException extends FileDeleteException {
    public FileDeleteErrorException() {
        super();
    }

    public FileDeleteErrorException(String s) {
        super(s);
    }

    public FileDeleteErrorException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FileDeleteErrorException(Throwable throwable) {
        super(throwable);
    }

    protected FileDeleteErrorException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
