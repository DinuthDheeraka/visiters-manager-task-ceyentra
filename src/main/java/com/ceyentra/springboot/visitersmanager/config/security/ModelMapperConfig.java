/**
 * @author :  Dinuth Dheeraka
 * Created : 7/8/2023 2:08 AM
 */
package com.ceyentra.springboot.visitersmanager.config.security;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
