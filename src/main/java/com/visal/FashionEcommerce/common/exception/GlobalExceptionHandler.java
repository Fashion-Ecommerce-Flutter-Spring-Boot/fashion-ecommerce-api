package com.visal.FashionEcommerce.common.exception;

import com.visal.FashionEcommerce.common.response.ApiResponse;
import com.visal.FashionEcommerce.common.response.ApiResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
            MethodArgumentNotValidException e
    ){
        // Get the first validation error message
        String errMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Invalid request data");

        ApiResponse<Void> response =ApiResponseUtil.error(
                400,
                errMessage
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotfound(
            ResourceNotFoundException e
    ){
        ApiResponse<Void> response = ApiResponseUtil.error(
                404,
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(
            BadRequestException e
    ){
        ApiResponse<Void> response = ApiResponseUtil.error(
                400,
                e.getMessage()
        );

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<Void>> handleConflictRequest(
            ConflictException e
    ){
        ApiResponse<Void> response = ApiResponseUtil.error(
                409,
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /*
    public ResponseEntity<ApiResponse<Void>> handleUnauthorized(
            UnauthorizedException e
    ){
        ApiResponse<Void> response = ApiResponseUtil.error(
                401,
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

    }
    */
}
