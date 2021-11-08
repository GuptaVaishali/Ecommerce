package com.ttn.project.ecommerce.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone,String> {

    @Override
    public boolean isValid(String phoneValue, ConstraintValidatorContext constraintValidatorContext) {
        if(phoneValue.matches
                ("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$"))
            return true;
        return false;
    }
}
