package org.cqlin.exceptionhandlerdemo.exception;

import org.cqlin.exceptionhandlerdemo.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DemoException.class)
    public Result DemoExceptionHandler(DemoException e) {
        return Result.error("全局");
    }
}
