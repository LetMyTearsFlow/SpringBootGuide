package org.cqlin.exceptionhandlerdemo.controller;

import org.cqlin.exceptionhandlerdemo.common.Result;
import org.cqlin.exceptionhandlerdemo.exception.DemoException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {
    @RequestMapping("/hello")
    public Result hello() {
        return Result.ok("ok");
    }

    @RequestMapping("/hello/exception")
    public Result exception() {
        throw new RuntimeException("somehow i want to raise an exception");
    }

    @ExceptionHandler(RuntimeException.class)
    public Result runTimeExceptionHandler(RuntimeException e) {
        return Result.error(e.getMessage());
    }

    @RequestMapping("/hello/demo/exception")
    public Result demoExceptionRaiser() {
        throw new DemoException();
    }
}
