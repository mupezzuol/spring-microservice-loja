package br.com.microservice.loja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.microservice.loja.entities.Purchase;
import br.com.microservice.loja.model.dto.PurchaseDTO;
import br.com.microservice.loja.service.IPurchaseService;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

	@Autowired
	private IPurchaseService purchaseService;
	
	@GetMapping("/{id}")
	public Purchase getPurchaseById(@PathVariable("id") Long id) {
		return this.purchaseService.findPurchaseById(id);
	}
	
	@PostMapping
	public Purchase makePurchase(@RequestBody PurchaseDTO purchase) {
		return this.purchaseService.makePurchase(purchase);
	}
	
}

