package br.com.microservice.loja.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.microservice.loja.model.dto.InfoProviderDTO;

@FeignClient("fornecedor") //name-id that eureka server
public interface ProviderClient {
	
	@RequestMapping("/info/{state}")
	InfoProviderDTO getInfoProviderByState(@PathVariable String state);

}
