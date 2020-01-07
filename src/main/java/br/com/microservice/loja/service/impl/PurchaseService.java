package br.com.microservice.loja.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.microservice.loja.client.ProviderClient;
import br.com.microservice.loja.model.Purchase;
import br.com.microservice.loja.model.dto.InfoProviderDTO;
import br.com.microservice.loja.model.dto.PurchaseDTO;
import br.com.microservice.loja.model.dto.infoOrderDTO;
import br.com.microservice.loja.service.IPurchaseService;

@Service
public class PurchaseService implements IPurchaseService{
	
	@Autowired
	private ProviderClient providerClient;

	@Override
	public Purchase makePurchase(PurchaseDTO purchase) {
		
		InfoProviderDTO infoProviderByState = this.providerClient.getInfoProviderByState(purchase.getAddress().getState());
		
		infoOrderDTO order = this.providerClient.placeOrder(purchase.getItems());
		
		System.out.println("Address: " + infoProviderByState.getAddress());
		
		Purchase purchaseSave = new Purchase(
				order.getId(),
				order.getPreparationTime(),
				purchase.getAddress().toString());
		
		return purchaseSave;
	}

}
