package com.example.infosystems.model.validator;

import com.example.infosystems.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String> {
    private final EmployeeService employeeService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(username)) {
            return true;
        }
        return this.employeeService.isUserNameFree(username);
    }
}
