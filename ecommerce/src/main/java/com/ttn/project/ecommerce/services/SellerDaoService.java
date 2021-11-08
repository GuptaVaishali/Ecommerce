package com.ttn.project.ecommerce.services;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ttn.project.ecommerce.entities.registration.Address;
import com.ttn.project.ecommerce.entities.registration.Role;
import com.ttn.project.ecommerce.entities.registration.Seller;
import com.ttn.project.ecommerce.exceptions.UserNotFoundException;
import com.ttn.project.ecommerce.repos.AddressRepository;
import com.ttn.project.ecommerce.repos.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SellerDaoService {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    AddressRepository addressRepository;

    public Seller createSeller(Seller seller){
        seller.setPassword(passwordEncoder.encode(seller.getPassword()));
        Role role = new Role();
        role.setAuthority("ROLE_SELLER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        seller.setRoles(roles);
        return sellerRepository.save(seller);
    }

    public List<Seller> getAllSellers(){
        return (List<Seller>) sellerRepository.findAll();
    }


    public MappingJacksonValue viewSellerProfile(Long id)
    {
        Optional<Seller> seller = sellerRepository.findById(id);

        //invoking static method filterOutAllExcept()
        if (!seller.isPresent())
            throw new UserNotFoundException("Seller not found");

        SimpleBeanPropertyFilter filter=SimpleBeanPropertyFilter.filterOutAllExcept(
                "id", "firstName","lastName","email","isActive","gst",
                "companyName","companyContact","city","state","country",
                "addressLine","zipCode" //,"addresses"
        );

        //creating filter using FilterProvider class
        FilterProvider filters = new SimpleFilterProvider().addFilter("SellerFilter",filter);

        //constructor of MappingJacksonValue class  that has bean as constructor argument
        MappingJacksonValue mapping = new MappingJacksonValue(seller);

        //configuring filters
        mapping.setFilters(filters);
        return mapping;
    }

    public String updateSellerProfile(Long id , Seller seller){
        Optional<Seller> getSeller = sellerRepository.findById(id);

        if (!getSeller.isPresent())
            throw new UserNotFoundException("Seller not found to update the profile");

        Seller seller1 = getSeller.get();
        if (seller.getFirstName() != null)
            seller1.setFirstName(seller.getFirstName());

        if (seller.getCompanyContact() != null)
            seller1.setCompanyContact(seller.getCompanyContact());

        if (seller.getLastName() != null)
            seller1.setLastName(seller.getLastName());

        if (seller.getGst() != null)
            seller1.setGst(seller.getGst());

        if (seller.getCompanyName() != null)
            seller1.setCompanyName(seller.getCompanyName());

        sellerRepository.save(seller1);
        return "Profile updated successfully";
    }

    public String updatePassword(Long id, String password, String confirmPassword){

        Optional<Seller> sellerById = sellerRepository.findById(id);
        if (sellerById.isPresent()){
            Seller seller = sellerById.get();
            String oldPassword = seller.getPassword();
            String newPassword = passwordEncoder.encode(password);
            System.out.println(newPassword);
            if(password.equals(confirmPassword)) {
                if (!oldPassword.equals(newPassword)) {
                    seller.setPassword(newPassword);
                    sellerRepository.save(seller);

                    SimpleMailMessage mailMessage = new SimpleMailMessage();
                    mailMessage.setTo(seller.getEmail());
                    mailMessage.setSubject("Password Updated");
                    mailMessage.setFrom("vaishgupta97@gmail.com");
                    mailMessage.setText("Password updated Successfully");
                    emailSenderService.sendEmail(mailMessage);
                    return "Password updated successfully";
                }
                else
                    return "Old password and new Password are same";
            }
            else
                return "Password and confirm Password do not match";
        }
        else
            throw new UserNotFoundException("Seller not found");
    }


    public String updateAddress(Long id, Long address_id, Address address){
        Optional<Address> byId = addressRepository.findById(address_id);
        if (byId.isPresent()) {
            Address address1 = byId.get();
            if (address.getCity() != null)
                address1.setCity(address.getCity());
            if (address.getAddressLine() != null)
                address1.setAddressLine(address.getAddressLine());
            if (address.getState() != null)
                address1.setState(address.getState());
            if (address.getCountry() != null)
                address1.setCountry(address.getCountry());
            if (address.getZipCode()!= null)
                address1.setZipCode(address.getZipCode());
            if (address.getLabel() != null)
                address1.setLabel(address.getLabel());

            addressRepository.save(address1);
            return "Address updated successfully";
        }
        else
            return "Address does not exist in db";
    }


}
