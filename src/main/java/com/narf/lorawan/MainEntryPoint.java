package com.narf.lorawan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;

@SpringBootApplication(exclude = DispatcherServletAutoConfiguration.class)
public class MainEntryPoint {

	public static void main(String[] args) {
		SpringApplication.run(MainEntryPoint.class, args);
	}
}
