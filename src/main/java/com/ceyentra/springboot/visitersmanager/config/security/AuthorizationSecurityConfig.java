/**
 * @author :  Dinuth Dheeraka
 * Created : 7/7/2023 8:48 PM
 */
package com.ceyentra.springboot.visitersmanager.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthorizationSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/floors/v1/**").hasRole("ADMIN")
                        .requestMatchers("/floors/v2/**").hasAnyRole("ADMIN", "RECEPTIONIST")

                        .requestMatchers("/visitor_cards/v1/**").hasRole("ADMIN")
                        .requestMatchers("/visitor_cards/v2/**").hasAnyRole("ADMIN", "RECEPTIONIST")

                        .requestMatchers("/visits/v1/**").hasRole("ADMIN")
                        .requestMatchers("/visits/v2/**").hasAnyRole("ADMIN", "RECEPTIONIST")

                        .requestMatchers("/visitors/v1/**").hasRole("ADMIN")
                        .requestMatchers("/visitors/v2/**").hasAnyRole("ADMIN", "RECEPTIONIST")

                        .requestMatchers("/users/v2/**").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .requestMatchers("/users/v1/**").hasRole("ADMIN")

                        .anyRequest().authenticated());

        http.httpBasic(Customizer.withDefaults());

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
