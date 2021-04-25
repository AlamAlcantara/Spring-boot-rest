/**
 * 
 */
package com.example.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.javafaker.Faker;

/**
 * @author alamalcanta
 *
 */
@Configuration
public class FakerBeanConfig {
	
	@Bean
	public Faker getFaker() {
		return new Faker();
	}
	

}
