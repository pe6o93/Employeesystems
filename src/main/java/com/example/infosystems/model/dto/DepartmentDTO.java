package com.example.infosystems.model.dto;

import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class DepartmentDTO {

    private Integer id;
    @Size(min=3,max=60,message = "Името на отделът трябва да е между 3 и 60 символа.")
    private String name;
    private String description;
    @Positive(message = "Моля въведете положително число")
    private Integer directorateId;
    @Positive(message = "Моля въведете положително число")
    private Integer bossId;
    private Boolean isActive;
}
