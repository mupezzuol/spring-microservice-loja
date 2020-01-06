package br.com.microservice.loja.service;

import br.com.microservice.loja.model.dto.PurchaseDTO;

public interface IPurchaseService {
	
	public void makePurchase(PurchaseDTO purchase);

}
