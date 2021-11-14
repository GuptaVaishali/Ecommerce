package com.ttn.project.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    AppUserDetailsService appUserDetailsService;

    public ResourceServerConfig() {
        super();
    }

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(appUserDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").anonymous()
                .antMatchers("/register-customer").permitAll()
                .antMatchers("/confirm-customer").permitAll()
                .antMatchers("/resend-link").permitAll()
                .antMatchers("/register-seller").permitAll()
                .antMatchers("/forgot-password-token").permitAll()
                .antMatchers("/change-password").permitAll()

                .antMatchers("/admin/home").hasAnyRole("ADMIN")
                .antMatchers("/customer/home").hasAnyRole("CUSTOMER")
                .antMatchers("/seller/home").hasAnyRole("SELLER")

                .antMatchers("/get-customers").hasAnyRole("ADMIN")
                .antMatchers("/get-sellers").hasAnyRole("ADMIN")
                .antMatchers("/activate-customer").hasAnyRole("ADMIN")
                .antMatchers("/deactivate-customer").hasAnyRole("ADMIN")
                .antMatchers("/activate-seller").hasAnyRole("ADMIN")
                .antMatchers("/deactivate-seller").hasAnyRole("ADMIN")
                .antMatchers("/{userId}/create-address").hasAnyRole("ADMIN")

                .antMatchers("/view-seller-profile").hasAnyRole("SELLER")
                .antMatchers("/update-seller-profile").hasAnyRole("SELLER")
                .antMatchers("/update-seller-password").hasAnyRole("SELLER")
                .antMatchers("/update-seller-address/{address_id}").hasAnyRole("SELLER")

                .antMatchers("/view-customer-profile").hasAnyRole("CUSTOMER")
                .antMatchers("/view-customer-addresses").hasAnyRole("CUSTOMER")
                .antMatchers("/update-customer-profile").hasAnyRole("CUSTOMER")
                .antMatchers("/update-customer-password").hasAnyRole("CUSTOMER")
                .antMatchers("/update-customer-address/{address_id}").hasAnyRole("CUSTOMER")
                .antMatchers("/add-customer-address").hasAnyRole("CUSTOMER")
                .antMatchers("/delete-customer-address/{address_id}").hasAnyRole("CUSTOMER")


                .antMatchers("/create-category").hasAnyRole("ADMIN")
                .antMatchers("/view-category/{categoryId}").hasAnyRole("ADMIN")
                .antMatchers("/view-all-categories").hasAnyRole("ADMIN","SELLER","CUSTOMER")
                .antMatchers("/update-category/{categoryId}/{categoryName}").hasAnyRole("ADMIN")

                .antMatchers("/create-metadatafield").hasAnyRole("ADMIN")
                .antMatchers("/view-metadatafields").hasAnyRole("ADMIN")
                .antMatchers("/create-metadatafieldvalue/{categoryId}/{metadataFieldId}").hasAnyRole("ADMIN")
                .antMatchers("/update-metadatafieldvalue/{categoryId}/{metadataFieldId}").hasAnyRole("ADMIN")

                .antMatchers("/add-product").hasAnyRole("SELLER")
                .antMatchers("/view-product/{productId}").hasAnyRole("SELLER","CUSTOMER","ADMIN")
                .antMatchers("/view-all-products").hasAnyRole("SELLER","CUSTOMER","ADMIN")
                .antMatchers("/update-product/{productId}").hasAnyRole("SELLER")
                .antMatchers("/delete-product/{productId}").hasAnyRole("SELLER")
                .antMatchers("/deactivate-product/{productId}").hasAnyRole("ADMIN")
                .antMatchers("/activate-product/{productId}").hasAnyRole("ADMIN")
                .antMatchers("/similar-products/{productId}").hasAnyRole("CUSTOMER")

                .antMatchers("/create-product-variation").hasAnyRole("SELLER")
                .antMatchers("/view-product-variation/{productVariationId}").hasAnyRole("SELLER")
                .antMatchers("/view-all-product-variations/{productId}").hasAnyRole("SELLER")
                .antMatchers("/update-product-variation/{productVariationId}").hasAnyRole("SELLER")

                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable();
    }

}

