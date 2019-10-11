package com.sanghye.webservice.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

@Component
public class webSecurityConfigure extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity auth) throws Exception {
        auth.authorizeRequests()
                .antMatchers("/", "/users").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }
}
