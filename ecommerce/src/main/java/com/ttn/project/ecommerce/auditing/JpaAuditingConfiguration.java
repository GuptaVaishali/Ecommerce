package com.ttn.project.ecommerce.auditing;

import com.ttn.project.ecommerce.entities.registration.Customer;
import com.ttn.project.ecommerce.entities.registration.User;
import com.ttn.project.ecommerce.security.AppUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

        @Bean
        public AuditorAware<String> auditorProvider(){

        //    return () -> Optional.ofNullable("vaishali");

            return new AuditorAwareImpl();
        }

}
