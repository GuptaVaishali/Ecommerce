package com.ttn.project.ecommerce.security;

import com.ttn.project.ecommerce.entities.registration.User;
import com.ttn.project.ecommerce.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email);
//        if(user == null)
//            throw new UsernameNotFoundException(
//                    String.format("Username with email %s not found",email));
//        return user;

        Optional<User> user = userRepository.findByEmail(email);
        user.orElseThrow(()->new UsernameNotFoundException("Email not found"));
        return user.map(users->new AppUser(users))
                .get();


//        return userRepository.findByEmail(email)
//                .orElseThrow(()->new UsernameNotFoundException(
//                        String.format("Username with email %s not found",email));
    }


}
