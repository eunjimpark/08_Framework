package edu.kh.project.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {
	
	
	@Bean
	public BCryptPasswordEncoder bCrtBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}