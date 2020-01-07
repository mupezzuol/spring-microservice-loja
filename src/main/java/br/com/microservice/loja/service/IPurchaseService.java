package br.com.microservice.loja.service;

import br.com.microservice.loja.model.Purchase;
import br.com.microservice.loja.model.dto.PurchaseDTO;

public interface IPurchaseService {
	
	public Purchase makePurchase(PurchaseDTO purchase);

}
