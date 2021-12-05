package com.alkemy.ong.config;

import com.alkemy.ong.utils.ValidatorUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfig {

    @Bean
    public ValidatorUtil ValidatorUtil(){
        return new ValidatorUtil();
    }
}
