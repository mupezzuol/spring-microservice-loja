package br.com.microservice.loja.service;

import br.com.microservice.loja.entities.Purchase;
import br.com.microservice.loja.model.dto.PurchaseDTO;

public interface IPurchaseService {
	
	public Purchase makePurchase(PurchaseDTO purchase);
	
	public Purchase reprocessPurchase(PurchaseDTO purchase);
	
	public Purchase cancelPurchase(PurchaseDTO purchase);
	
	public Purchase findPurchaseById(Long id);

}
