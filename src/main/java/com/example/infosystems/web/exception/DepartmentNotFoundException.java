package com.example.infosystems.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DepartmentNotFoundException extends RuntimeException {

    public DepartmentNotFoundException(Integer id) {
        super("Не може да се открие отдел с id=" + id);
    }

    public DepartmentNotFoundException(String name) {
        super("Не може да се открие отдел с име: " + name);
    }
}
