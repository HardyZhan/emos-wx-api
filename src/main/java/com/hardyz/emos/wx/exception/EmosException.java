package com.hardyz.emos.wx.exception;

import lombok.Data;

@Data
public class EmosException extends RuntimeException{
    private String msg;
    private int code = 500;
    EmosException(String msg) {
        super(msg);
        this.msg = msg;
    }
    EmosException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }
    EmosException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }
}
