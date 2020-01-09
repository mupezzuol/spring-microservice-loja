package br.com.microservice.loja.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.microservice.loja.client.ProviderClient;
import br.com.microservice.loja.entities.Purchase;
import br.com.microservice.loja.model.dto.InfoProviderDTO;
import br.com.microservice.loja.model.dto.PurchaseDTO;
import br.com.microservice.loja.model.dto.infoOrderDTO;
import br.com.microservice.loja.repository.PurchaseRepository;
import br.com.microservice.loja.service.IPurchaseService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PurchaseService implements IPurchaseService{
	
	@Autowired
	private ProviderClient providerClient;
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Override
	@HystrixCommand(threadPoolKey = "findPurchaseByIdThreadPool")
	public Purchase findPurchaseById(Long id) {
		return this.purchaseRepository.findById(id).orElse(new Purchase());
	}

	@Override
	@HystrixCommand(fallbackMethod = "makePurchaseFallback", threadPoolKey = "makePurchaseThreadPool")
	public Purchase makePurchase(PurchaseDTO purchase) {
		final String state = purchase.getAddress().getState();
		
		log.info("seeking information from the {} " + state);
		InfoProviderDTO infoProviderByState = this.providerClient.getInfoProviderByState(state);
		
		log.info("placing an order");
		infoOrderDTO order = this.providerClient.placeOrder(purchase.getItems());
		
		Purchase purchaseSave = new Purchase(
				order.getId(),
				order.getPreparationTime(),
				infoProviderByState.getAddress().toString());
		
		this.purchaseRepository.save(purchaseSave);
		
		return purchaseSave;
	}
	
	//Method for Fallback and Circuit Braker
	public Purchase makePurchaseFallback(PurchaseDTO purchase) {
		Purchase purchaseFallback = new Purchase();
		purchaseFallback.setDestinationAddress(purchase.getAddress().toString());
		return purchaseFallback;
	}

	
}
