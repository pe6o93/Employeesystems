package com.example.infosystems.model.validator;

import com.example.infosystems.repository.EmployeeRepository;
import com.example.infosystems.service.EmployeeService;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class ValidPersonalNumberValidator implements ConstraintValidator<ValidPersonalNumber, String> {

    private final EmployeeRepository employeeRepository;

    @Override
    public boolean isValid(String personalNumber, ConstraintValidatorContext constraintValidatorContext) {
        if(!this.employeeRepository.findByEGN(personalNumber).isPresent()) {
            Matcher matcher = Pattern.compile("[0-9]{10}$").matcher(personalNumber);
            return matcher.find();
        }
        return false;
    }
}
