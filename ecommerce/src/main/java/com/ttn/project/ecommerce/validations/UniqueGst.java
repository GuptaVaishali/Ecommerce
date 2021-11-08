package com.ttn.project.ecommerce.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueGstValidator.class)
public @interface UniqueGst {

    String message() default "{Gst already in use}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
