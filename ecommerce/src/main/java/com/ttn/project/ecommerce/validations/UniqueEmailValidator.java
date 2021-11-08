package com.ttn.project.ecommerce.validations;

import com.ttn.project.ecommerce.entities.registration.User;
import com.ttn.project.ecommerce.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail,String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("inside isvalid call of unique  email");
        Optional<User> inDbuser = userRepository.findByEmail(value);
        System.out.println("validating email");
        if(!inDbuser.isPresent()) {
            System.out.println("inside if stemnt");
            return true;
        }
        System.out.println("after if sattement");
        return false;
//        User inDb = inDbuser.get();
//        System.out.println(inDb.getEmail() + " " + inDb.getFirstName());
//        if(inDb == null)
//            return true;
//        return false;
    }
}
