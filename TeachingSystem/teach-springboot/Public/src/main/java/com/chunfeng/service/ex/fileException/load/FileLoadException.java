package com.chunfeng.service.ex.fileException.load;

import com.chunfeng.service.ex.fileException.FileException;

/**
 * 文件上传异常超类
 */
public class FileLoadException extends FileException {
    public FileLoadException() {
        super();
    }

    public FileLoadException(String s) {
        super(s);
    }

    public FileLoadException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FileLoadException(Throwable throwable) {
        super(throwable);
    }

    protected FileLoadException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
