package com.ttn.project.ecommerce.validations;

import com.ttn.project.ecommerce.entities.registration.Seller;
import com.ttn.project.ecommerce.repos.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueCompanyValidator implements ConstraintValidator<UniqueCompany,String> {

    @Autowired
    SellerRepository sellerRepository;

    @Override
    public boolean isValid(String companyName, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Seller> inDb = sellerRepository.findByCompanyName(companyName);
        if(!inDb.isPresent()) {
            System.out.println("company name is not present");
            return true;
        }
        return false;
    }
}
