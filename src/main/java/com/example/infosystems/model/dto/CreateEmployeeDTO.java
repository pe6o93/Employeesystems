package com.example.infosystems.model.dto;

import com.example.infosystems.model.validator.UniqueUserName;
import com.example.infosystems.model.validator.ValidPersonalNumber;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateEmployeeDTO {

    @NotNull
    @Size(min = 3, max = 30, message = "Името трябва да бъде повече от 3 символа и по малко от 30.")
    private String firstName;

    @Size(min = 3, max = 30, message = "Фамилията трябва да бъде повече от 3 символа и по малко от 30.")
    private String lastName;

    @ValidPersonalNumber
    private String egn;

    @Min(value = 18, message = "Минималната възраст е 18 години.")
    private Byte age;

    private String departmentName;

    //private MultipartFile[] contracts;
    private Boolean isActive;

    @UniqueUserName
    @Size(min = 3, max = 30, message = "Потребителското име трябва да бъде повече от 3 символа и по малко от 30.")
    private String username;

    @Size(min = 3, max = 20, message = "Паролата трябва да бъде повече от 3 символа и по малко от 20.")
    private String password;

    private String position;
}
