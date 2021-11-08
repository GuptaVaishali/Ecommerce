package com.ttn.project.ecommerce.validations;

import com.ttn.project.ecommerce.entities.registration.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches,Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        User user = (User) obj;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
