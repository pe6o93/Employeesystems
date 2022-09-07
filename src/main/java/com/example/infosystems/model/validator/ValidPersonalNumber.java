package com.example.infosystems.model.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPersonalNumberValidator.class)
public @interface ValidPersonalNumber {

    String message() default "Невалидно ЕГН.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
