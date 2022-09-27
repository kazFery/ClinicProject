package com.medical.clinic.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)

public @interface UniqueEmail {
    String message() default "Email address is already registered";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}