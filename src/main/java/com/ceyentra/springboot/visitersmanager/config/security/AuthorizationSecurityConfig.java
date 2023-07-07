/**
 * @author :  Dinuth Dheeraka
 * Created : 7/7/2023 8:48 PM
 */
package com.ceyentra.springboot.visitersmanager.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthorizationSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET,"/").hasAnyRole("ADMIN","RECEPTIONIST")
                        .requestMatchers(HttpMethod.GET, "/d").hasRole("RECEPTIONIST")
                        .anyRequest().authenticated());

        http.httpBasic(Customizer.withDefaults());

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
