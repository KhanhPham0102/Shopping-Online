package com.app.security;

import com.app.dto.UserDto;
import com.app.model.JwtAuthenticationToken;
import com.app.model.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JwtValidator validator;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException { }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) usernamePasswordAuthenticationToken;

        String token = jwtAuthenticationToken.getToken();

        UserDto userDto = validator.validate(token);

        if (userDto == null) {

            return new JwtUserDetails(null,null,
                    token);
        }

        return new JwtUserDetails(userDto.getUserName(),userDto.getPassWord(),
                    token);
    }

    @Override
    public boolean supports(Class<?> aClass) {

        return (JwtAuthenticationToken.class.isAssignableFrom(aClass));
    }
}