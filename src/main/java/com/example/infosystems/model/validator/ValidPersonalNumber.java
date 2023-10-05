package com.example.infosystems.model.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPersonalNumberValidator.class)
public @interface ValidPersonalNumber {

    String message() default "Невалидно ЕГН.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
