package com.example.dummySbrigit;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DummySbrigitApplication {
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	@Value("${stripe.api.key}")
	private String stripeApiKey;
	@PostConstruct
	public void setup(){
		Stripe.apiKey= stripeApiKey;
	}

	public static void main(String[] args) {
		SpringApplication.run(DummySbrigitApplication.class, args);
	}

}
