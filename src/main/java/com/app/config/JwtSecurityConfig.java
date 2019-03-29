package com.app.config;

import com.app.security.JwtAuthenticationEntryPoint;
import com.app.security.JwtAuthenticationProvider;
import com.app.security.JwtAuthenticationTokenFilter;
import com.app.security.JwtSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;

import java.util.Collections;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Bean
    public AuthenticationManager authenticationManager() {

        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(new JwtAuthenticationTokenFilter("/users/**", authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(new JwtAuthenticationTokenFilter("/stores", authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(new JwtAuthenticationTokenFilter("/products/**", authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        http.headers().cacheControl();
    }

    @Override
    public void configure(WebSecurity web) {

        web.ignoring()
                .antMatchers(HttpMethod.POST, "/users")
                .antMatchers(HttpMethod.GET,"/stores/**")
                .antMatchers(HttpMethod.GET,"/products/**");
    }
}