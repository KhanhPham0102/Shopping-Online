package com.app.security;

import com.app.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {

    public String generate(UserDto userDto) {

        Claims claims = Jwts.claims()
                .setSubject(userDto.getUserName());

        claims.put("password", userDto.getPassWord());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "youtube")
                .compact();
    }
}