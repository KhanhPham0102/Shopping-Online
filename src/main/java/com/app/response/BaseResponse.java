package com.app.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponse<T> {

    private int status;

    private T data;

    private String msg;

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public BaseResponse(int status,T data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public static <T> BaseResponse<T> createSuccessResponse(String msg){
        return new BaseResponse<>(200,msg);
    }
    public static <T> BaseResponse<T> createErrorResponse(int status, String msg){
        return new BaseResponse<>(status,msg);
    }
    public static <T> BaseResponse<T> createBaseResponse(T data, String msg) {
        return new BaseResponse<>(200, data, msg);
    }
}
