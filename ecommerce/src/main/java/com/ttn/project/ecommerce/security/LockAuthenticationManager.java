//package com.ttn.project.ecommerce.security;
//
//import com.ttn.project.ecommerce.entities.registration.User;
//import com.ttn.project.ecommerce.repos.UserRepository;
//import com.ttn.project.ecommerce.services.EmailSenderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.LockedException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Collections;
//import java.util.Optional;
//
//@Configuration
//public class LockAuthenticationManager implements AuthenticationProvider {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    private EmailSenderService emailSenderService;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String email = authentication.getName();
//        System.out.println("inside lock authentication manager");
//        String password = authentication.getCredentials().toString();
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        Optional<User> byId = userRepository.findByEmail(email);
//        User user = byId.get();;
//        if(user == null)
//            throw new UsernameNotFoundException("Email is not correct");
//
//        if (user.getEmail().equals(email) && passwordEncoder.matches(password,user.getPassword())) {
//            return new UsernamePasswordAuthenticationToken(email, password, Collections.emptyList());
//        }
//        else {
//            Integer numberOfAttempts = user.getInvalidAttemptCount();
//            user.setInvalidAttemptCount(++numberOfAttempts);
//            userRepository.save(user);
//
//            if (user.getInvalidAttemptCount() >= 3) {
//                user.setLocked(false);
//                userRepository.save(user);
//
//                SimpleMailMessage mailMessage = new SimpleMailMessage();
//                mailMessage.setTo(user.getEmail());
//                mailMessage.setSubject("Account Locked");
//                mailMessage.setFrom("vaishgupta97@gmail.com");
//                mailMessage.setText("Your account has been locked !!");
//                emailSenderService.sendEmail(mailMessage);
//
//                throw new LockedException("User account is locked");
//            }
//            throw new BadCredentialsException("Password is incorrect");
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return aClass.equals(UsernamePasswordAuthenticationToken.class);
//    }
//}
