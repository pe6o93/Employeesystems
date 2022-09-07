package com.example.infosystems.model.dto;

import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class DirectorateDTO {

    private Integer id;
    @Size(max=60,message = "Името на дирекцията трябва да е по-малко от 60 символа")
    private String name;
    private String description;
    @Positive(message = "Моля въведете положително число")
    private Integer directorId;
    private Boolean isActive;
}
