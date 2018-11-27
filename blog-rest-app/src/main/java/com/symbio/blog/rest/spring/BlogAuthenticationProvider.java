package com.symbio.blog.rest.spring;

import com.symbio.blog.domain.user.User;
import com.symbio.blog.infrastructure.springdata.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BlogAuthenticationProvider implements AuthenticationProvider {

    private static final String BAD_CREDENTIAL_ERROR = "incorrect username/password or status is inactive!";

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = this.userRepository.findByName(username);

        try {
            if (!user.getPassword().equals(password)) {
                throw new BadCredentialsException(BAD_CREDENTIAL_ERROR);
            }
        } catch (NoSuchElementException e) {
            throw new BadCredentialsException(BAD_CREDENTIAL_ERROR);
        }

        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        String[] roles = user.getRoles().split(";");
        for (String role : roles) {
            grantedAuths.add(new SimpleGrantedAuthority(role.trim()));
        }

        return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
