package com.example.infosystems.model.validator;

import com.example.infosystems.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class ValidPersonalNumberValidator implements ConstraintValidator<ValidPersonalNumber, String> {

    private final EmployeeService employeeService;
    private final String PERSONAL_NUMBER_REGEX = "[0-9]{10}$";

    @Override
    public boolean isValid(final String personalNumber,
                           final ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isNotBlank(personalNumber)) {
            if (this.employeeService.isExistingEmployee(personalNumber)) {
                Matcher matcher = Pattern.compile(PERSONAL_NUMBER_REGEX).matcher(personalNumber);
                return matcher.find();
            }
        }
        return false;
    }
}
