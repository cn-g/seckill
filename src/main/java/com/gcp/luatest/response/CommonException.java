package com.gcp.luatest.response;

/**
 * 自定义异常
 * 
 * @author xu
 */
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Object data;
    private int code;
    private String message;

    public CommonException(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public CommonException(String message, int code, Object data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public CommonException() {
        this.code = CodeMsg.CommonException.getCode();
        this.message = CodeMsg.CommonException.getMessage();
    }

    public CommonException(String message) {
        this.code = CodeMsg.CommonException.getCode();
        this.message = message;
    }

    public CommonException(int code) {
        this.message = CodeMsg.CommonException.getMessage();
        this.code = code;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return this.data;
    }

    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("\"code\":%s,\"message\":\"%s\"", this.code, this.message);
    }

}
