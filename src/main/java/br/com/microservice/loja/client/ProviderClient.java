package br.com.microservice.loja.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.microservice.loja.model.dto.InfoProviderDTO;
import br.com.microservice.loja.model.dto.PurchaseItemDTO;
import br.com.microservice.loja.model.dto.infoOrderDTO;

@FeignClient("fornecedor") //name-id that eureka server
public interface ProviderClient {
	
	@RequestMapping("/info/{state}")
	InfoProviderDTO getInfoProviderByState(@PathVariable String state);

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	infoOrderDTO placeOrder(List<PurchaseItemDTO> items);

}
