package com.ttn.project.ecommerce.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    String message() default "{Password must be between 8 and 15 characters long and should contain " +
            "at least one number," +
            "one lowercase,one uppercase,and one special character}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
