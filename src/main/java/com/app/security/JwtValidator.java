package com.app.security;

import com.app.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    private String secret = "youtube";

    public UserDto validate(String token) {

        UserDto userDto = null;

        try {

            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            userDto = new UserDto();

            userDto.setUserName(body.getSubject());

            userDto.setPassWord((String) body.get("password"));
        } catch (Exception ignored) {
        }

        return userDto;
    }
}