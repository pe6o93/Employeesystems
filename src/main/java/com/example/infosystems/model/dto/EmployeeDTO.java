package com.example.infosystems.model.dto;


import com.example.infosystems.model.validator.ValidPersonalNumber;
import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class EmployeeDTO {

    private Integer id;
    @NotNull
    @Size(min = 3, max = 30, message = "Името трябва да бъде повече от 3 символа и по малко от 30.")
    private String firstName;
    @Size(min = 3, max = 30, message = "Фамилията трябва да бъде повече от 3 символа и по малко от 30.")
    private String lastName;
    @Size(max = 10)
    @ValidPersonalNumber
    private String EGN;
    @Min(value = 18, message = "Минималната възраст е 18 години.")
    private Byte age;
    private String departmentName;
    private Boolean isActive;
}
