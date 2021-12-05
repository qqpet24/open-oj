package com.xmu.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.springframework.http.HttpStatus.valueOf;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public Object handleException(Throwable e){
        log.error(getStackTrace(e));
        return buildResponseEntity(ApiError.error(e.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public Object badCredentialsException(BadCredentialsException e){
        String message = "Bad credentials".equals(e.getMessage()) ? "用户名或密码不正确" : e.getMessage();
        log.error(message);
        return buildResponseEntity(ApiError.error(message));
    }

    private String getStackTrace(Throwable e){
        StringWriter sw=new StringWriter();
        try (PrintWriter pw=new PrintWriter(sw)){
            e.printStackTrace(pw);
            return sw.toString();
        }
    }

    private Object buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, valueOf(apiError.getStatus()));
    }
}
