package br.com.microservice.loja.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfigure extends ResourceServerConfigurerAdapter {
	
	/*
	 How to get to the authentication server? 
	 
	 Response: In the 'application.yml' file we added the setting to make
	 this authentication call on the authentication server 'spring.microservice.auth'
	 
	 Ex: security.oauth2.resource.user-info-uri: http://localhost:8088/user 
	*/
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/purchase")
			.hasRole("USER");
	}

	
}
