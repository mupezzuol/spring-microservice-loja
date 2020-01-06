package br.com.microservice.loja.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.microservice.loja.controller.dto.PurchaseDTO;
import lombok.extern.slf4j.Slf4j;

@RestController(value = "/purchase")
@Slf4j
public class PurchaseController {

	@PostMapping
	public void makePurchase(@RequestBody PurchaseDTO purchase) {
		log.info("Make Purchase");
	}
	
}

