package com.example.infosystems.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DirectorateNotFoundException extends RuntimeException{

    public DirectorateNotFoundException(Integer id) {
        super("Не може да се открие дирекция с id=" + id);
    }

    public DirectorateNotFoundException(String message) {
        super(message);
    }
}
