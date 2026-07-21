package org.cqlin.exceptionhandlerdemo.common;

import lombok.Data;

@Data
public class Result<T> {
    Integer code;
    String message;
    T data;

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.message = "ok";
        result.data = data;
        return result;
    }

    public static <T> Result<T> error(T data) {
        Result<T> result = new Result<>();
        result.code = 500;
        result.message = "異常";
        result.data = data;
        return result;
    }
}
