package com.visal.FashionEcommerce.common.exception;

public class CloudinaryUploadException extends RuntimeException{
    public CloudinaryUploadException(String message){
        super(message);
    }

    public CloudinaryUploadException(String message, Throwable cause){
        super(message, cause);
    }
}
