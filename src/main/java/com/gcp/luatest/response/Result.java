package com.gcp.luatest.response;

import java.io.Serializable;

/**
 * 接口返回数据结构
 * 
 * @author xu
 * @param <T>
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private String message;

    private CodeMsg codeMsg;

    private T data;

    public Result codeMsg(CodeMsg codeMsg) {
        this.code = codeMsg.getCode();
        this.message = codeMsg.getMessage();
        return this;
    }

    /**
     * 成功提示
     * 
     * @return
     */
    public Result<T> ok() {
        this.codeMsg(CodeMsg.OK);
        return this;
    }

    public Result<T> ok(T data) {
        this.data = data;
        return this.ok();

    }

    public Result message(String message) {
        this.message = message;
        return this;
    }

    public Result code(int code) {
        this.code = code;
        return this;
    }

    public Result data(T data) {
        this.data = data;
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }

    public T getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCodeMsg(CodeMsg responseCode) {
        this.codeMsg = responseCode;
    }

    public void setData(T data) {
        this.data = data;
    }

}
