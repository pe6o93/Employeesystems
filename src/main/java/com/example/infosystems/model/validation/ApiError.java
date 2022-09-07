package com.example.infosystems.model.validation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ApiError {

    private final HttpStatus status;
    private Map<String, String> fieldWithErrors = new HashMap<>();

    public ApiError(HttpStatus status) {
        this.status = status;
    }

    public void addFieldWithError(String fieldName,String errorMessage) {
        this.fieldWithErrors.put(fieldName, errorMessage);
    }
}