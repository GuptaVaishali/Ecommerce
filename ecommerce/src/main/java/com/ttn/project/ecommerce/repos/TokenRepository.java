package com.ttn.project.ecommerce.repos;

import com.ttn.project.ecommerce.entities.registration.Token;
import org.springframework.data.repository.CrudRepository;


public interface TokenRepository extends CrudRepository<Token,Long> {

    Token findByToken(String token);

    Token  findByEmail(String email);

    Token findByForgotPassToken(String token);

}
