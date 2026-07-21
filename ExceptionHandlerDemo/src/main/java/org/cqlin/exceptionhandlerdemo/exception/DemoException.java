package org.cqlin.exceptionhandlerdemo.exception;

import lombok.Getter;

public class DemoException extends RuntimeException {
    @Getter
    private Integer code = 403;
    @Getter
    private String message = "demo exception";
}
