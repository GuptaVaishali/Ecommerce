package com.ttn.project.ecommerce.services;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ttn.project.ecommerce.entities.registration.*;
import com.ttn.project.ecommerce.exceptions.UserNotFoundException;
import com.ttn.project.ecommerce.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerDaoService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    AddressRepository addressRepository;

//    @Bean
//    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }


    //create customer user
    public Customer createCustomerUser(Customer customer){
        System.out.println("inside customerdaoserrvice creating customer user");
        List<Role> roles = new ArrayList<>();
//        Role role  = roleRepository.findById(3l).get();
//        System.out.println(">>>>>>>>>>>>>>>>>>>." + role.getAuthority());
//        roles.add(role);

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        System.out.println(">>>>>>>>>>>>>  Password encoded " + passwordEncoder.encode(customer.getPassword()));
        Role role1 = new Role();
        role1.setAuthority("ROLE_CUSTOMER");
        roles.add(role1);
        customer.setRoles(roles);

//        List<Address> addresses = new ArrayList<>();
//        Address address = new Address();
//        address.setAddressLine("house no. 123");
//        address.setCity("ballabgarh");
//        address.setCountry("India");
//        address.setLabel("home");
//        address.setState("Haryana");
//        address.setZipCode("121004");
//        addresses.add(address);
//        address.setUser(customer);

//        customer.setAddresses(addresses);
        return customerRepository.save(customer);
    }


    /* Perform Read Operation on Entity using Spring Data JPA */
    public List<Customer> readAllCustomers(){
        return (List<Customer>) customerRepository.findAll();
    }

    public Token generateReToken(String email){
        Optional<User> user1 = userRepository.findByEmail(email);
        User user = user1.get();
        Token reToken = tokenService.createToken(user);
        tokenRepository.save(reToken);
        return reToken;
    }

    public MappingJacksonValue viewCustomerProfile(Long id)
    {
        Optional<Customer> customer = customerRepository.findById(id);

        //invoking static method filterOutAllExcept()
        if (!customer.isPresent())
            throw new UserNotFoundException("Customer not found");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(
                "id", "firstName","lastName","email","isActive","contact"
        );

        //creating filter using FilterProvider class
        FilterProvider filters = new SimpleFilterProvider().addFilter("CustomerFilter",filter);

        //constructor of MappingJacksonValue class  that has bean as constructor argument
        MappingJacksonValue mapping = new MappingJacksonValue(customer);

        //configuring filters
        mapping.setFilters(filters);
        return mapping;
    }

    public MappingJacksonValue viewCustomerAddresses(Long id){

        Optional<Customer> customer = customerRepository.findById(id);

        //invoking static method filterOutAllExcept()
        if (!customer.isPresent())
            throw new UserNotFoundException("Customer not found");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(
                "addresses"
        );

        //creating filter using FilterProvider class
        FilterProvider filters = new SimpleFilterProvider().addFilter("CustomerFilter",filter);

        //constructor of MappingJacksonValue class  that has bean as constructor argument
        MappingJacksonValue mapping = new MappingJacksonValue(customer);

        //configuring filters
        mapping.setFilters(filters);
        return mapping;
    }


    public String updateCustomerProfile(Long id , Customer customer){
        Optional<Customer> getCustomer = customerRepository.findById(id);

        if (!getCustomer.isPresent())
            throw new UserNotFoundException("Customer not found to update the profile");

        Customer customer1 = getCustomer.get();
        if (customer.getFirstName() != null)
            customer1.setFirstName(customer.getFirstName());

        if (customer.getMiddleName() != null)
            customer1.setMiddleName(customer.getMiddleName());

        if (customer.getLastName() != null)
            customer1.setLastName(customer.getLastName());

        if (customer.getEmail() != null)
            customer1.setEmail(customer.getEmail());

        if (customer.getContact() != null)
            customer1.setContact(customer.getContact());

        customerRepository.save(customer1);
        return "Profile updated successfully";
    }


    public String updatePassword(Long id, String password, String confirmPassword){

        Optional<Customer> customerById = customerRepository.findById(id);
        if (customerById.isPresent()){
            Customer customer = customerById.get();
            String oldPassword = customer.getPassword();
            String newPassword = passwordEncoder.encode(password);
            System.out.println(newPassword);
            if(password.equals(confirmPassword)) {
                if (!oldPassword.equals(newPassword)) {
                    customer.setPassword(newPassword);
                    customerRepository.save(customer);

                    SimpleMailMessage mailMessage = new SimpleMailMessage();
                    mailMessage.setTo(customer.getEmail());
                    mailMessage.setSubject("Password Updated");
                    mailMessage.setFrom("vaishgupta97@gmail.com");
                    mailMessage.setText("customer Password updated Successfully");
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

    public String addAddressForUser(Long id, Address address){
        Optional<Customer> customer = customerRepository.findById(id);
        if (!customer.isPresent())
            throw new UserNotFoundException("User not found with id " + id);
        else{
            Customer customer1 = customer.get();
//            List<Address> addresses = new ArrayList<>();
            address.setUser(customer1);
//            addresses.add(address);
//            customer1.setAddresses(addresses);
            addressRepository.save(address);
            return "Address added for customer successfully";
        }
    }

    public String deleteAddressForUser(Long id, Long address_id){
        Optional<Customer> customer = customerRepository.findById(id);
        if (!customer.isPresent())
            throw new UserNotFoundException("User not found with id " + id);
        else{
            Optional<Address> address = addressRepository.findById(address_id);
            if (address.isPresent()){
                Address address1 = address.get();
                List<Address> add = customer.get().getAddresses();
                add = add.stream().filter(p->p.getId() != address_id).collect(Collectors.toList());
                customer.get().setAddresses(add);

                userRepository.save(customer.get());
                addressRepository.deleteById(address_id);
                return "Address deleted for customer successfully";
            }
            else
                return "Address with addressId" + address_id + "does not exist";

        }
    }



}
