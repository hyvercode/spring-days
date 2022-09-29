package com.hyvercode.springday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableFeignClients
public class SpringDayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDayApplication.class, args);
	}

}
