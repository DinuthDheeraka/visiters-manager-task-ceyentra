/**
 * @author :  Dinuth Dheeraka
 * Created : 7/7/2023 8:48 PM
 */
package com.ceyentra.springboot.visitersmanager.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class LoginSecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        //query to retrieve user by user name * regular sql
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select user_name,password,active from user where user_name=?"
        );

        //query to retrieve role by given user name * regular sql
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_name,user_type from user where user_name=?"
        );
        return jdbcUserDetailsManager;
    }

}
