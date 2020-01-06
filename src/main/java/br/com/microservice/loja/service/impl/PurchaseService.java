package br.com.microservice.loja.service.impl;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.microservice.loja.model.dto.InfoProviderDTO;
import br.com.microservice.loja.model.dto.PurchaseDTO;
import br.com.microservice.loja.service.IPurchaseService;

@Service
public class PurchaseService implements IPurchaseService{

	@Override
	public void makePurchase(PurchaseDTO purchase) {
		
		RestTemplate client = new RestTemplate();
		
		ResponseEntity<InfoProviderDTO> exchange = client.exchange("http://localhost:8081/info/"+purchase.getAddress().getState(),
				HttpMethod.GET, null, InfoProviderDTO.class);
		
		System.out.println("Address: " + exchange.getBody().getAddress());
		
	}

}
