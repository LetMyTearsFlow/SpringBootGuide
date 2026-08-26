package org.cqlin.datavalidationdemo.common;

public record ApiResponse<T>(int code, String message, T data) {

    /**
     * Creates a successful API response.
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "success", data);
    }

    /**
     * Creates a failed API response.
     */
    public static <T> ApiResponse<T> failure(int code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }
}
