package br.com.microservice.loja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.client.RestTemplate;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableResourceServer
@EnableSwagger2
public class SpringMicroserviceLojaApplication {
	
	// Add config to intercept Feign requests for when we call another microservices to be passed the authentication token
	// Add config in file 'application.yml'-> hystrix.shareSecurityContext: true
	@Bean
	public RequestInterceptor getInterceptorDeAutenticacao() {
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate template) {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if(authentication == null) {
					return;
				}
				
				OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)authentication.getDetails();
				template.header("Authorization", "Bearer" + details.getTokenValue());
			}
		};
	}
	
	@Bean
	@LoadBalanced //he will do the intelligence needed to use application names in eureka
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringMicroserviceLojaApplication.class, args);
	}

}
