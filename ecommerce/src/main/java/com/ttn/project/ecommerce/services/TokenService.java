package com.ttn.project.ecommerce.services;

import com.ttn.project.ecommerce.entities.registration.Token;
import com.ttn.project.ecommerce.entities.registration.User;
import com.ttn.project.ecommerce.repos.TokenRepository;
import com.ttn.project.ecommerce.repos.UserRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {
    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
    private static final Charset US_ASCII = Charset.forName("US-ASCII");


    @Autowired
    TokenRepository tokenRepo;

    @Autowired
    UserRepository userRepo;

//    public Token createToken(String email){
//        String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII); // this is a sample, you can adapt as per your security need
//        User u=userRepo.findByEmail(email);
//        Token token = new Token(u);
//        token.setToken(tokenValue);
//        token.setExpireAt(LocalDateTime.now().plusSeconds(999999999));
//    //    User u=userRepo.findByEmail(email);
//    //    token.setUser(u);
//        token.setEmail(email);
//        tokenRepo.save(token);
//        return token;
//
//    }

    public Token createToken(User user){
        String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII);
        Token token = new Token(user);
        token.setToken(tokenValue);
        System.out.println("token value " + tokenValue);
        token.setExpireAt(LocalDateTime.now().plusSeconds(10800));
    //    token.setExpireAt(LocalDateTime.now().plusSeconds(1l));
        token.setEmail(user.getEmail());
        tokenRepo.save(token);
        return token;
    }


    public Token generateNewToken(Token token){
        String newTokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII); // this is a sample, you can adapt as per your security need
        Token newToken = new Token(token.getUser());
        newToken.setToken(newTokenValue);
        System.out.println("newToken value " + newTokenValue);
        newToken.setExpireAt(LocalDateTime.now().plusSeconds(10800));
        newToken.setEmail(token.getUser().getEmail());
        tokenRepo.save(newToken);

//        token.setExpireAt(LocalDateTime.now().plusSeconds(10800));
//        token.setToken(newTokenValue);
//        tokenRepo.save(token);
        return newToken;
    }

    public Token createForgotPasswordToken(String email){
        String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII); // this is a sample, you can adapt as per your security need
        Token forgotPasswordToken = new Token(userRepo.findByEmail(email).get());
        forgotPasswordToken.setForgotPassToken(tokenValue);
        System.out.println("token value " + tokenValue);
        forgotPasswordToken.setExpireAt(LocalDateTime.now().plusSeconds(900));
        //    token.setExpireAt(LocalDateTime.now().plusSeconds(1l));
        forgotPasswordToken.setEmail(email);
        tokenRepo.save(forgotPasswordToken);
        return forgotPasswordToken;
    }

    public String checkFpTokenValidity(String token){
        String str = null;
        Token fpToken = tokenRepo.findByForgotPassToken(token);
        if (fpToken == null){
            str =  "Token is not valid";
        }
        else{
            LocalDateTime now = LocalDateTime.now();
            if(ChronoUnit.SECONDS.between(now, fpToken.getExpireAt()) <= 0){
                str =  "Token has been expired";
                tokenRepo.delete(fpToken);
            }
        }
        return str;
    }


    public Token findTokenByForgetPasswordToken(String fpTokenVal){
        return tokenRepo.findByForgotPassToken(fpTokenVal);
    }
}
