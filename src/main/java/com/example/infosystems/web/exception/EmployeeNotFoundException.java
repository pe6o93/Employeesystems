package com.example.infosystems.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException{
   public EmployeeNotFoundException(Integer id) {
        super("Не може да се открие служител с id=" + id);
    }

   public EmployeeNotFoundException(String message) {
        super(message);
    }
}
