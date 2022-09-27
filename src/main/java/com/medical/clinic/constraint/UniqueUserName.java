package com.medical.clinic.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Constraint(validatedBy = UniqueUserNameValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)

public @interface UniqueUserName {
    String message() default "Username is already registered";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}