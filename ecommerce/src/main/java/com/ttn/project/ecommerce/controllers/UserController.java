package com.ttn.project.ecommerce.controllers;

import com.ttn.project.ecommerce.entities.registration.Address;
import com.ttn.project.ecommerce.entities.registration.Customer;
import com.ttn.project.ecommerce.entities.registration.Token;
import com.ttn.project.ecommerce.entities.registration.User;
import com.ttn.project.ecommerce.services.EmailSenderService;
import com.ttn.project.ecommerce.services.TokenService;
import com.ttn.project.ecommerce.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDaoService.readAllUsers();
    }

//    creating and returning same user
//    @PostMapping("/users")
//    public User createUser(@RequestBody User user){
//        System.out.println(user.getFirstName());
//        User savedUser = userDaoService.createUser(user);
//        return savedUser;
//    }

    @GetMapping("/admin/home")
    public String adminHome(){
        System.out.println("inside admin home");
        return "Admin home";
    }

//    creating employee and send response status created 201.
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody User user){
        User savedUser = userDaoService.createUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Autowired
    TokenService tokenService;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    UserDaoService userDaoService;

    @Autowired
    TokenStore tokenStore;

    // To get Forgot password token
    @PostMapping("/forgot-password-token")
    public String sendForgotPasswordLink(@Valid @RequestParam String email){
        String str = userDaoService.checkEmail(email);
        if (str == null) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject("Forgot Password Token");
            mailMessage.setFrom("vaishgupta97@gmail.com");
            mailMessage.setText("To change your password, please click here : "
                    + "http://localhost:8080/change-password?token=" +
                    tokenService.createForgotPasswordToken(email).getForgotPassToken());

            emailSenderService.sendEmail(mailMessage);

            return "Please Check your mail to change password";
        }
        else
            return str;
    }

    // to change user password
    @PutMapping("/change-password")
    public String changePassword(@RequestParam("token") String fpToken,
                                     @RequestParam("password") String password,
                                     @RequestParam("confirmPassword") String confirmPassword) {

        String str = tokenService.checkFpTokenValidity(fpToken);
        // if token is valid
        if (str == null) {
            Token token = tokenService.findTokenByForgetPasswordToken(fpToken);
            if (token != null) {
                String passStatus =
                        userDaoService.changeUserPassword(token,token.getUser(),password, confirmPassword);
                // if password changed successfully
                if (passStatus.equals("password changed")) {
                    SimpleMailMessage mailMessage = new SimpleMailMessage();
                    mailMessage.setTo(token.getUser().getEmail());
                    mailMessage.setSubject("Password Updated");
                    mailMessage.setFrom("vaishgupta97@gmail.com");
                    mailMessage.setText("Password updated Successfully");
                    emailSenderService.sendEmail(mailMessage);
                    return "Please check your mail to know password change status";
                }
                else
                    return passStatus;
            }
            return "user does not exists having this token";
        } else
            return str;
    }

    @GetMapping("/get-customers")
    public MappingJacksonValue getCustomers(@RequestParam(name = "pageSize",required = false) String pageSize,
                                            @RequestParam(name = "pageOffset" ,required = false) String pageOffset,
                                            @RequestParam(name= "sortBy", required = false) String sortBy) {
        return userDaoService.getCustomers(pageSize, pageOffset, sortBy);
    }

    @GetMapping("/get-sellers")
    public MappingJacksonValue getSellers() {
        return userDaoService.getSellers();
    }

    @PostMapping("/{userId}/create-address")
    public String createAddress(@PathVariable long userId, @RequestBody Address address){
        return userDaoService.createAddress(userId, address);
    }

    @PatchMapping("/activate-customer")
    public String activateCustomer(@RequestParam long custId){
        return userDaoService.activateCustomer(custId);
    }

    @PatchMapping("/deactivate-customer")
    public String deActivateCustomer(@RequestParam long custId){
        return userDaoService.deActivateCustomer(custId);
    }

    @PatchMapping("/activate-seller")
    public String activateSeller(@RequestParam long sellerId){
        return userDaoService.activateSeller(sellerId);
    }

    @PatchMapping("/deactivate-seller")
    public String deActivateSeller(@RequestParam long sellerId){
        return userDaoService.deActivateSeller(sellerId);
    }


    @GetMapping("/doLogout")
    public String logout(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
        return "Logged out successfully";
    }

    @GetMapping("/find-current-customer")
    public String findCurrentCustomer(){
        Customer customer = userDaoService.getLoggedInCustomer();
        return customer.getEmail() + " " + customer.getId() + " " + customer.getPassword() + " " +
                customer.getFirstName() + " " + customer.getRoles();
    }

}
