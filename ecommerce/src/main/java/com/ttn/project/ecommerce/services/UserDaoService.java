package com.ttn.project.ecommerce.services;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ttn.project.ecommerce.entities.registration.*;
import com.ttn.project.ecommerce.exceptions.UserNotFoundException;
import com.ttn.project.ecommerce.repos.AddressRepository;
import com.ttn.project.ecommerce.repos.CustomerRepository;
import com.ttn.project.ecommerce.repos.SellerRepository;
import com.ttn.project.ecommerce.repos.UserRepository;
import com.ttn.project.ecommerce.security.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDaoService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    EmailSenderService emailSenderService;

    //create user
    public User createUser(User user){
        List<Role> roles = new ArrayList<>();
        Role role1 = new Role();
        role1.setAuthority("ROLE_ADMIN");
        roles.add(role1);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
     }


    /* Perform Read Operation on Entity using Spring Data JPA */
    public List<User> readAllUsers(){
        return (List<User>) userRepository.findAll();
    }

    public String checkEmail(String email){
        Optional<User> user1 = userRepository.findByEmail(email);
        User user = user1.get();
        String str = null;
        if(user == null) {
            str =  "Email does not exist in db";
        }else {
            if (user.isActive() == false)
                str = "User is not active";
        }
        return str;
    }

    public void changeUserPassword(User user, String password){
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        System.out.println("changed Password " + user.getPassword());
    }

    public MappingJacksonValue getCustomers(String pageSize, String pageOffset,
                                            String sortBy, String email) {
        Pageable pageable = PageRequest.of(Integer.parseInt(pageSize),
                Integer.parseInt(pageOffset),
                Sort.by(new Sort.Order(Sort.Direction.DESC,"id")));
        List<Customer> customerList = customerRepository.findAll(pageable);

        //invoking static method filterOutAllExcept()
        SimpleBeanPropertyFilter filter=SimpleBeanPropertyFilter.filterOutAllExcept(
                "id", "firstName","middleName","lastName","email","isActive","contact");

        //creating filter using FilterProvider class
        FilterProvider filters=new SimpleFilterProvider().addFilter("customerFilter",filter);

        //constructor of MappingJacksonValue class  that has bean as constructor argument
        MappingJacksonValue mapping = new MappingJacksonValue(customerList);

        //configuring filters
        mapping.setFilters(filters);
        return mapping;
    }

    public MappingJacksonValue getSellers()
    {
        List<Seller> sellerList = (List<Seller>) sellerRepository.findAll();

        //invoking static method filterOutAllExcept()
        SimpleBeanPropertyFilter filter=SimpleBeanPropertyFilter.filterOutAllExcept(
                "id", "firstName","middleName","lastName","email","isActive",
                "companyName","companyContact" //,"addresses"
                 );

        //creating filter using FilterProvider class
        FilterProvider filters=new SimpleFilterProvider().addFilter("SellerFilter",filter);

        //constructor of MappingJacksonValue class  that has bean as constructor argument
        MappingJacksonValue mapping = new MappingJacksonValue(sellerList);

        //configuring filters
        mapping.setFilters(filters);
        return mapping;
    }

    public String createAddress(Long id, Address address){
        List<Address> addressList = new ArrayList<>();
        User user = userRepository.findById(id).get();
        address.setUser(user);
        addressRepository.save(address);
        addressList.add(address);
        user.setAddresses(addressList);
        return "Address created successfully";
    }

    public String activateCustomer(@RequestParam long custId){
        Optional<User> getuser = userRepository.findById(custId);
        if(!getuser.isPresent()) {
            throw new UserNotFoundException("customer does not exist with id " + custId);
        }
        else{
            User user = getuser.get();
            if (user.isActive() == false){
                user.setActive(true);
                userRepository.save(user);
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(user.getEmail());
                mailMessage.setSubject("Admin Send Customer Activation Mail");
                mailMessage.setFrom("vaishgupta97@gmail.com");
                mailMessage.setText("Account Activated Successfully");
                emailSenderService.sendEmail(mailMessage);
                return "Customer activated";
            }
            else
                return "Customer is already activated";
        }
    }

    public String deActivateCustomer(@RequestParam long custId){
        Optional<User> getuser = userRepository.findById(custId);
        if(!getuser.isPresent()) {
            throw new UserNotFoundException("customer does not exist with id " + custId);
        }
        else{
            User user = getuser.get();
            if (user.isActive() == true){
                user.setActive(false);
                userRepository.save(user);
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(user.getEmail());
                mailMessage.setSubject("Admin Send Customer DeActivation Mail");
                mailMessage.setFrom("vaishgupta97@gmail.com");
                mailMessage.setText("Your Customer Account DeActivated");
                emailSenderService.sendEmail(mailMessage);
                return "Customer Account Deactivated";
            }
            else
                return "Customer is already DeActivated";
        }
    }

    public String activateSeller(@RequestParam Long sellerId){
        Optional<User> getuser = userRepository.findById(sellerId);
        if(!getuser.isPresent()) {
            throw new UserNotFoundException("seller does not exist with id " + sellerId);
        }
        else{
            User user = getuser.get();
            if (user.isActive() == false){
                user.setActive(true);
                userRepository.save(user);
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(user.getEmail());
                mailMessage.setSubject("Admin Send Seller Activation Mail");
                mailMessage.setFrom("vaishgupta97@gmail.com");
                mailMessage.setText("Account Activated Successfully");
                emailSenderService.sendEmail(mailMessage);
                return "Seller Account activated";
            }
            else
                return "Seller Account is already activated";
        }
    }

    public String deActivateSeller(@RequestParam long sellerId){
        Optional<User> getuser = userRepository.findById(sellerId);
        if(!getuser.isPresent()) {
            throw new UserNotFoundException("seller does not exist with id " + sellerId);
        }
        else{
            User user = getuser.get();
            if (user.isActive() == true){
                user.setActive(false);
                userRepository.save(user);
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(user.getEmail());
                mailMessage.setSubject("Admin Send Seller DeActivation Mail");
                mailMessage.setFrom("vaishgupta97@gmail.com");
                mailMessage.setText("Your Seller Account DeActivated");
                emailSenderService.sendEmail(mailMessage);
                return "Seller Account Deactivated";
            }
            else
                return "Seller is already DeActivated";
        }
    }


    public Customer getLoggedInCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) authentication.getPrincipal();
        String email = appUser.getUsername();
        Optional<User> byEmailUser= userRepository.findByEmail(email);
        User customerUser = byEmailUser.get();
        Customer customer1=(Customer)customerUser;

        return customer1;
    }

    public Seller getLoggedInSeller() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) authentication.getPrincipal();
        String email = appUser.getUsername();
        Optional<User> byEmailUser= userRepository.findByEmail(email);
        User sellerUser = byEmailUser.get();
        Seller seller1=(Seller)sellerUser;

        return seller1;
    }

}
