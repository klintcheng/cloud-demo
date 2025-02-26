package com.mygb.user.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
    // Map<String, Object> response = new HashMap<>();
    // response.put("timestamp", System.currentTimeMillis());
    // response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    // response.put("error", "Internal Server Error");
    // response.put("message", ex.getMessage()); // 返回自定义错误消息，不返回trace

    // // 可选择是否需要详细日志或其他处理逻辑
    // // response.put("trace", ex.getStackTrace());

    // return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    // }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
