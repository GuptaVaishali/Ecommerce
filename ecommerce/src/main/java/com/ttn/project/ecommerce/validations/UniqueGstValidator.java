package com.ttn.project.ecommerce.validations;

import com.ttn.project.ecommerce.entities.registration.Seller;
import com.ttn.project.ecommerce.repos.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueGstValidator implements ConstraintValidator<UniqueGst,String> {

    @Autowired
    SellerRepository sellerRepository;

    @Override
    public boolean isValid(String gstValue, ConstraintValidatorContext constraintValidatorContext) {
        Seller gstInDb = sellerRepository.findByGst(gstValue);
        if(gstInDb == null)
            return true;
        return false;
    }
}
