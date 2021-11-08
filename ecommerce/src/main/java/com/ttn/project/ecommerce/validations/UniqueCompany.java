package com.ttn.project.ecommerce.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCompanyValidator.class)
public @interface UniqueCompany {

    String message() default "{Company name should be unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
