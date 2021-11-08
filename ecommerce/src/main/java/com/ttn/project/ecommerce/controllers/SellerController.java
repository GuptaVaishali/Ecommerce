package com.ttn.project.ecommerce.controllers;

import com.ttn.project.ecommerce.entities.registration.Address;
import com.ttn.project.ecommerce.entities.registration.Seller;
import com.ttn.project.ecommerce.services.EmailSenderService;
import com.ttn.project.ecommerce.services.SellerDaoService;
import com.ttn.project.ecommerce.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class SellerController {

    @Autowired
    SellerDaoService sellerDaoService;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    UserDaoService userDaoService;

    @GetMapping("/sellerusers")
    public List<Seller> retrieveAllSellers() {
        return sellerDaoService.getAllSellers();
    }

//    // creating and returning same user
//    @PostMapping("/register-seller")
//    public Seller createSeller(@Valid @RequestBody Seller seller){
//        Seller savedSeller = sellerDaoService.createSeller(seller);
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(seller.getEmail());
//        mailMessage.setSubject(" Seller Activation Mail");
//        mailMessage.setFrom("vaishgupta97@gmail.com");
//        mailMessage.setText("Account has been created, waiting for approval");
//
//        emailSenderService.sendEmail(mailMessage);
//        return savedSeller;
//    }

    // creating and returning same user
    @PostMapping("/register-seller")
    public ResponseEntity<Object> createSeller(@Valid @RequestBody Seller seller){
        Seller savedSeller = sellerDaoService.createSeller(seller);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(seller.getEmail());
        mailMessage.setSubject("Seller Activation Mail");
        mailMessage.setFrom("vaishgupta97@gmail.com");
        mailMessage.setText("Account has been created, waiting for approval");

        emailSenderService.sendEmail(mailMessage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("{id}")
                .buildAndExpand(savedSeller.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/view-seller-profile")
    public MappingJacksonValue viewSellerProfile(){
        Seller seller = userDaoService.getLoggedInSeller();
        Long id = seller.getId();
        return sellerDaoService.viewSellerProfile(id);
    }

    @PatchMapping("/update-seller-profile")
    public String updateSellerProfile(@RequestBody Seller seller){
        Seller loggedInSeller = userDaoService.getLoggedInSeller();
        Long id = loggedInSeller.getId();
        return sellerDaoService.updateSellerProfile(id,seller);
    }


    @PatchMapping("/update-seller-password")
    public String updatePassword(@Valid @RequestParam String password, @Valid @RequestParam String confirmPassword){
        Seller loggedInSeller = userDaoService.getLoggedInSeller();
        Long id = loggedInSeller.getId();
        return sellerDaoService.updatePassword(id, password, confirmPassword);
    }

    @PatchMapping("/update-seller-address/{address_id}")
    public String updateAddress(@PathVariable Long address_id,@RequestBody Address address){
        Seller loggedInSeller = userDaoService.getLoggedInSeller();
        Long id = loggedInSeller.getId();
        return sellerDaoService.updateAddress(id,address_id, address);
    }

}
