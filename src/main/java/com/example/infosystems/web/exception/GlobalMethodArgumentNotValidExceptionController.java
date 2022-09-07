package com.example.infosystems.web.exception;

import com.example.infosystems.model.validation.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalMethodArgumentNotValidExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> onValidationFailure(MethodArgumentNotValidException exc) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        exc.getFieldErrors().forEach(fieldError -> apiError.addFieldWithError(fieldError.getField(), Objects.requireNonNull(fieldError.getDefaultMessage())));
        return ResponseEntity
                .badRequest().body(apiError);
    }
}
