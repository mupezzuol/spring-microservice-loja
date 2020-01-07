package br.com.microservice.loja.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.microservice.loja.client.ProviderClient;
import br.com.microservice.loja.model.dto.InfoProviderDTO;
import br.com.microservice.loja.model.dto.PurchaseDTO;
import br.com.microservice.loja.service.IPurchaseService;

@Service
public class PurchaseService implements IPurchaseService{
	
	@Autowired
	private ProviderClient providerClient;

	@Override
	public void makePurchase(PurchaseDTO purchase) {
		
		InfoProviderDTO infoProviderByState = this.providerClient.getInfoProviderByState(purchase.getAddress().getState());
		
		System.out.println("Address: " + infoProviderByState.getAddress());
	}

}
