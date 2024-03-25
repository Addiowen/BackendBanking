package com.example.bankingtest.security;


import com.example.bankingtest.service.Userservice;
import com.example.bankingtest.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class CustomUserDetails implements UserDetailsService {
    @Autowired
    private Userservice userservice;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        boolean isValid;

        User user = (User) Optional.ofNullable(userservice.getUserUsingId(username)).orElse(new User());
        isValid = (user.getPIN() != null && user.getId() != null);
        List<GrantedAuthority> authorities = new ArrayList<>();

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return authorities;
            }

            @Override
            public String getPassword() {
                return user.getPIN();
            }

            @Override
            public String getUsername() {
                return user.getId();

            }

            @Override
            public boolean isAccountNonExpired() {
                return isValid;
            }

            @Override
            public boolean isAccountNonLocked() {
                return isValid;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return isValid;
            }

            @Override
            public boolean isEnabled() {
                return isValid;
            }
        };
    }
}
