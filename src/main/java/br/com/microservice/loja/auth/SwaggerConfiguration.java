package br.com.microservice.loja.auth;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

	@Bean
	public Docket forumApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.microservice.loja.controller"))
				.paths(PathSelectors.any())
				.build()
				.ignoredParameterTypes(disableTemplateClassesModels())
				.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
        	.title("Spring Microservice - Loja API")
            .description("Microservices Project Using: Spring Boot + Eureka (registry/discovery) + Spring Cloud (config server) "
            		+ "+ Feign (Clientside LoadBalance) + Logs and distributed tracing with Papertrail "
            		+ "+ Sleuth + Hystrix (Fallback and Circuit Breaker) + API Gateway + Spring Zuul "
            		+ "+ Authentication and Authorization between microservices using OAuth2.")
            .contact(new Contact("Murillo Pezzuol", "https://github.com/mupezzuol/", "murillopezzuol@hotmail.com"))
            .license("License 1.0.0")
            .licenseUrl("https://github.com/mupezzuol/spring-microservice-loja")
            .version("1.0.0")
            .build();
    }
	
	//Method that returns templates that will be hidden in the API documentation
	@SuppressWarnings("rawtypes")
	private Class[] disableTemplateClassesModels(){
		ArrayList<Class> classForDisable = new ArrayList<Class>();
		
		//Entities - Models - DTO and others...
		
		return classForDisable.toArray(new Class[classForDisable.size()]);
	}
	
}
