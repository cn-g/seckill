package com.gcp.luatest.response;

/**
 * 自定义响应枚举
 * 
 * @author xu
 */
public enum CodeMsg {

    OK(200, "OK"), CommonException(503, "业务异常"), NOPOWER(403, "权限不足"),
    LOGINEXCEPTION(402, "认证失败");

    private int code;

    private String message;


    private CodeMsg(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "{" + "code=" + code + ", message='" + message + '\''
            + '}';
    }

}
