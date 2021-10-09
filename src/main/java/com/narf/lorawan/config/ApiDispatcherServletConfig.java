package com.narf.lorawan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;

@Configuration
public class ApiDispatcherServletConfig {

    @Bean
    public DispatcherServlet apiDispatcherServlet() {
        return new DispatcherServlet();
    }
    
    @Bean
    public DispatcherServletRegistrationBean apiDispatcherServletRegistration() {
    
        DispatcherServletRegistrationBean registration = new DispatcherServletRegistrationBean(apiDispatcherServlet(), "/api/*");
        registration.setName("apiDispatcherServlet");
    
        return registration;
    }
}
