package com.ttn.project.ecommerce.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GstValidator implements ConstraintValidator<Gst,String> {

    @Override
    public boolean isValid(String gstValue, ConstraintValidatorContext constraintValidatorContext) {

//      ^ represents the starting of the string.
//      [0-9]{2} represents the first two characters should be a number.
//      [A-Z]{5} represents the next five characters should be any upper case alphabets.
//      [0-9]{4} represents the next four characters should be any number.
//      [A-Z]{1} represents the next character should be any upper case alphabet.
//      [1-9A-Z]{1} represents the 13th character should be a number from 1-9 or an alphabet.
//      Z represents the 14th character should be Z.
//      [0-9A-Z]{1} represents the 15th character should be an alphabet or a number.
//      $ represents the ending of the string.

        if(gstValue.matches("^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$"))
            return true;
        return false;
    }
}
