package com.wangrj.yunpan.bean;

/**
 * by wangrongjun on 2018/9/2.
 */
public class ApiResponse<T> {

    private int statusCode;
    private String message;
    private T data;

    @SuppressWarnings("unchecked")
    public static ApiResponse ok() {
        return new ApiResponse(0, null, null);
    }

    @SuppressWarnings("unchecked")
    public static ApiResponse ok(String message) {
        return new ApiResponse(0, message, null);
    }

    public static <T> ApiResponse<T> data(T data) {
        return new ApiResponse<>(0, null, data);
    }

    @SuppressWarnings("unchecked")
    public static ApiResponse error(String message) {
        return new ApiResponse(-1, message, null);
    }

    public ApiResponse() {
    }

    public ApiResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
