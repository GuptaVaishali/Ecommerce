package com.ttn.project.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class AuthenticationManagerProvider extends WebSecurityConfigurerAdapter {


    private final LockAuthenticationManager lockAuthenticationManager;

    @Autowired
    public AuthenticationManagerProvider(LockAuthenticationManager lockAuthenticationManager) {
        this.lockAuthenticationManager = lockAuthenticationManager;
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
