package com.app.security;

import com.app.model.JwtAuthenticationToken;
import com.app.model.JwtUserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

    public JwtAuthenticationTokenFilter(String url, AuthenticationManager authManager) {

        super(new AntPathRequestMatcher(url));

        setAuthenticationManager(authManager);

        setAuthenticationSuccessHandler(new JwtSuccessHandler());

        setAuthenticationFailureHandler(new JwtFailureHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {

        String header = httpServletRequest.getHeader("Authorization");

        if (header == null || !header.startsWith("Token ")) {

            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Please pass valid jwt token.");

            return null;
        }

        String authenticationToken = header.substring(6);

        JwtAuthenticationToken token = new JwtAuthenticationToken(authenticationToken);

        Authentication authResult = getAuthenticationManager().authenticate(token);

        Object authResponse = authResult.getPrincipal();

        if (new ModelMapper().map(authResponse, JwtUserDetails.class).getUsername() == null) {

            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Token is incorrect");

            return null;
        }

        return authResult;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,AuthenticationException exception) throws IOException, ServletException {

        super.unsuccessfulAuthentication(request, response , exception);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        super.successfulAuthentication(request, response, chain, authResult);

        chain.doFilter(request, response);
    }
}