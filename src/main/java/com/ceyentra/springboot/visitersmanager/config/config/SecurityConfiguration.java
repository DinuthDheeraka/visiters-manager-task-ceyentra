package com.ceyentra.springboot.visitersmanager.config.config;

import com.ceyentra.springboot.visitersmanager.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.ceyentra.springboot.visitersmanager.enums.UserPermission.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/v1/auth/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html"
                )
                .permitAll()

                 //auth visitor end-points
                .requestMatchers("/api/v1/visitors/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.RECEPTIONIST.name())

                .requestMatchers(GET, "/api/v1/visitors/**").hasAnyAuthority(ADMIN_READ.name(), RECEPTIONIST_READ.name())
                .requestMatchers(POST, "/api/v1/visitors/**").hasAnyAuthority(ADMIN_CREATE.name(), RECEPTIONIST_CREATE.name())
                .requestMatchers(PUT, "/api/v1/visitors/**").hasAnyAuthority(ADMIN_UPDATE.name(), RECEPTIONIST_UPDATE.name())
                .requestMatchers(DELETE, "/api/v1/visitors/**").hasAnyAuthority(ADMIN_DELETE.name(), RECEPTIONIST_DELETE.name())

                 //auth visits end-points
                .requestMatchers("/api/v1/visits/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.RECEPTIONIST.name())

                .requestMatchers(GET, "/api/v1/visits/**").hasAnyAuthority(ADMIN_READ.name(), RECEPTIONIST_READ.name())
                .requestMatchers(POST, "/api/v1/visits/**").hasAnyAuthority(ADMIN_CREATE.name(), RECEPTIONIST_CREATE.name())
                .requestMatchers(PUT, "/api/v1/visits/**").hasAnyAuthority(ADMIN_UPDATE.name(), RECEPTIONIST_UPDATE.name())
                .requestMatchers(DELETE, "/api/v1/visits/**").hasAnyAuthority(ADMIN_DELETE.name())

                //auth visitor card end-points
                .requestMatchers("/api/v1/visitor_cards/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.RECEPTIONIST.name())

                .requestMatchers(GET, "/api/v1/visitor_cards/**").hasAnyAuthority(ADMIN_READ.name(), RECEPTIONIST_READ.name())
                .requestMatchers(POST, "/api/v1/visitor_cards/**").hasAnyAuthority(ADMIN_CREATE.name())
                .requestMatchers(PUT, "/api/v1/visitor_cards/**").hasAnyAuthority(ADMIN_UPDATE.name())
                .requestMatchers(DELETE, "/api/v1/visitor_cards/**").hasAnyAuthority(ADMIN_DELETE.name())

                //auth floor end-points
                .requestMatchers("/api/v1/floors/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.RECEPTIONIST.name())

                .requestMatchers(GET, "/api/v1/floors/**").hasAnyAuthority(ADMIN_READ.name(), RECEPTIONIST_READ.name())
                .requestMatchers(POST, "/api/v1/floors/**").hasAnyAuthority(ADMIN_CREATE.name())
                .requestMatchers(PUT, "/api/v1/floors/**").hasAnyAuthority(ADMIN_UPDATE.name())
                .requestMatchers(DELETE, "/api/v1/floors/**").hasAnyAuthority(ADMIN_DELETE.name())


                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                ////With stateless sessions, the server does not store session-related
                // information on the server-side.
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                ////JWT authentication filter (jwtAuthFilter) to the Spring Security filter
                // chain before the UsernamePasswordAuthenticationFilter.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        ;

        return http.build();
    }
}
