package com.netcracker.mycosts.config;


import com.netcracker.mycosts.entities.Role;
import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.services.UserService;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/js/**", "/css/**", "/images/**", "/error**").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserService userService) {
        return map -> {
            String id = (String) map.get("sub");
            User user = userService.getUserById(id);
            if (user == null) {
                user = new User();
                user.setId(id);
                user.setName((String) map.get("name"));
                user.setEmail((String) map.get("email"));
                user.setActive(true);
                user.setRoles(Collections.singleton(Role.USER));
                return userService.create(user);
            }
            else {
                return userService.create(user);
            }
        };
    }
}






