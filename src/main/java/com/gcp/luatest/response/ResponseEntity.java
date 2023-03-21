package com.gcp.luatest.response;

/**
 * 封装返回结果
 * 
 * @author xu
 */
public class ResponseEntity {

    public ResponseEntity() {

    }

    /**
     * 创建新对象
     * 
     * @return
     */
    public static Result newInstance() {
        Result result = new Result();
        return result;
    }

    /**
     * 返回对象中的内容
     * 
     * @param responseCode
     * @return
     */
    public static Result newInstance(CodeMsg responseCode) {
        return newInstance().codeMsg(responseCode);
    }

    /**
     * 实体出参封装
     * 
     * @param date
     * @param <T>
     * @return
     */
    public static <T> Result<T> ok(T date) {
        return newInstance().ok(date);
    }

    /**
     * 无参数返回封装
     * 
     * @return
     */
    public static Result ok() {
        return newInstance().ok();
    }

    /**
     * 业务异常封装
     * 
     * @param <T>
     * @return
     */
    public static <T> Result<T> commonException() {
        Result<T> responseModel = new Result();
        responseModel.codeMsg(CodeMsg.CommonException);
        return responseModel;
    }

    /**
     * 业务异常信息封装
     * 
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result<T> commonException(String message) {
        Result<T> result = new Result();
        result.codeMsg(CodeMsg.CommonException).message(message);
        return result;
    }

    public static <T> Result<T> commonException(CommonException commonException) {
        Result<T> result = new Result();
        result.codeMsg(CodeMsg.CommonException)
            .message(commonException.getMessage()).code(commonException.getCode()).data(commonException.getData());
        return result;
    }

    /**
     * 登录异常
     * 
     * @return
     */
    public static Result loginException() {
        Result result = new Result();
        result.codeMsg(CodeMsg.LOGINEXCEPTION);
        return result;
    }

    /**
     * 无权限
     * 
     * @return
     */
    public static Result noPowerException() {
        Result result = new Result();
        result.codeMsg(CodeMsg.NOPOWER);
        return result;
    }

}
