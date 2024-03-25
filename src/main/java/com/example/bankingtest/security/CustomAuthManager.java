package com.example.bankingtest.security;


import com.example.bankingtest.service.Userservice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;


@Configuration
@Slf4j
public class CustomAuthManager implements AuthenticationProvider {
    @Autowired
    private Userservice userservice;
    @Autowired
    private CustomUserDetails userDetails;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            String userId = authentication.getName();
            String pin = (String) authentication.getCredentials();

            UserDetails user = userDetails.loadUserByUsername(userId);

            if (!(user.getPassword().equals(pin) && user.getUsername().equals(userId))) {
                throw new BadCredentialsException("Invalid Credentials");
            } else {
                if (user.isEnabled()) {
                    return successfulAuthentication(authentication, user);
                } else {
                    throw new AccountExpiredException("Invalid Credentials");
                }
            }
        } catch (Exception e) {
         log.info(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private Authentication successfulAuthentication(final Authentication authentication, final UserDetails user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword(),
                user.getAuthorities());

        token.setDetails(authentication.getDetails());
        return token;
    }
}
