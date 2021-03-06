package com.davide.falcone.fabricktest.config;

import com.davide.falcone.fabricktest.service.AccountService;
import com.davide.falcone.fabricktest.service.AccountServiceImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.davide.falcone.fabricktest")
public class BeanConfiguration {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public AccountService accountService(){
		return new AccountServiceImpl();
	}

}
