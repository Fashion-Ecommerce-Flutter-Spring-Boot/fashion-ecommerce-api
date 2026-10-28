package com.visal.FashionEcommerce.common.response;

public class ApiResponseUtil {
    public static <T> ApiResponse<T> success(
            String message,
            T data
    ){
        return new ApiResponse<>(
                200,
                message,
                data
        );
    }


    public static <T> ApiResponse<T> created(
            String message,
            T data
    ){
        return new ApiResponse<>(
                201,
                message,
                data
        );
    }

    public static  <T> ApiResponse <T> error(
            int status,
            String message
    ){
        return new ApiResponse<>(
                status,
                message,
                null
        );
    }
}
