package com.ttn.project.ecommerce.auditing;

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
            /*
            get currently logged-in user via this
            SecurityContextHolder.getContext().getAuthentication().getName()
            */
            return () -> Optional.ofNullable("vaishali");

//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            AppUser appUser = (AppUser) authentication.getPrincipal();
//            String name = appUser.getFirstName();
//            return () -> Optional.ofNullable(name);
        }
}
