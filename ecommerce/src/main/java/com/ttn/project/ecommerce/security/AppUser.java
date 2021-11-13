package com.ttn.project.ecommerce.security;

import com.ttn.project.ecommerce.entities.registration.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AppUser extends User implements UserDetails {

    //  private User user;

    public AppUser(User user) {
        //    this.user = user;
        super(user);
    }
    //  List<GrantAuthorityImpl> grantAuthorities;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//
//        getRoles().stream().map(role -> grantedAuthorities.add(
//                new SimpleGrantedAuthority(role.getAuthority())));
//
////       grantedAuthorities.add(
////               new SimpleGrantedAuthority("ROLE_ADMIN"));
//       return grantedAuthorities;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> roleNames =  super.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());

        //  roleNames.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return roleNames;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
