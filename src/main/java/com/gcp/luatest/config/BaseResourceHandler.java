package com.gcp.luatest.config;

import com.gcp.luatest.response.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 异常拦截
 * @author Admin
 */
@ControllerAdvice
public class BaseResourceHandler {

    private static final Logger log = LoggerFactory.getLogger(BaseResourceHandler.class);

    private <T> ResponseEntity<T> buildResponse(T t, HttpServletRequest request) {
        return ResponseEntity.ok(t);
    }

    @ExceptionHandler({CommonException.class})
    public Object handleBusinessException(HttpServletRequest request, HttpServletResponse response, CommonException e) throws IOException {
        return this.buildResponse(com.gcp.luatest.response.ResponseEntity.commonException(e), request);
    }

}
