package br.com.microservice.loja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.microservice.loja.model.dto.PurchaseDTO;
import br.com.microservice.loja.service.IPurchaseService;

@RestController(value = "/purchase")
public class PurchaseController {

	@Autowired
	private IPurchaseService purchaseService;
	
	@PostMapping
	public void makePurchase(@RequestBody PurchaseDTO purchase) {
		
		this.purchaseService.makePurchase(purchase);
		
	}
	
}

