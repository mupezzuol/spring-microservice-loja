package br.com.microservice.loja.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.microservice.loja.client.ProviderClient;
import br.com.microservice.loja.client.VoucherClient;
import br.com.microservice.loja.entities.Purchase;
import br.com.microservice.loja.model.dto.InfoDeliveryDTO;
import br.com.microservice.loja.model.dto.InfoOrderDTO;
import br.com.microservice.loja.model.dto.InfoProviderDTO;
import br.com.microservice.loja.model.dto.PurchaseDTO;
import br.com.microservice.loja.model.dto.VoucherDTO;
import br.com.microservice.loja.model.enums.PurchaseState;
import br.com.microservice.loja.repository.PurchaseRepository;
import br.com.microservice.loja.service.IPurchaseService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PurchaseService implements IPurchaseService{
	
	@Autowired
	private ProviderClient providerClient;
	
	@Autowired
	private VoucherClient voucherClient;
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Override
	@HystrixCommand(threadPoolKey = "findPurchaseByIdThreadPool")
	public Purchase findPurchaseById(Long id) {
		return this.purchaseRepository.findById(id).orElse(new Purchase());
	}
	
	@Override
	public Purchase reprocessPurchase(PurchaseDTO purchase) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Purchase cancelPurchase(PurchaseDTO purchase) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@HystrixCommand(fallbackMethod = "makePurchaseFallback", threadPoolKey = "makePurchaseThreadPool")
	public Purchase makePurchase(PurchaseDTO purchase) {
		final String state = purchase.getAddress().getState();
		
		// Step 0 - New
		Purchase purchaseSave = new Purchase();
		purchaseSave.setState(PurchaseState.RECEIVED);
		purchaseSave.setDestinationAddress(purchase.getAddress().toString());
		this.purchaseRepository.save(purchaseSave);
		
		// purchaseId for fallback
		purchase.setPurchaseId(purchaseSave.getId());
		
		
		// Step 1 - Information Fornecedor
		log.info("seeking information from the {} " + state);
		InfoProviderDTO infoProviderByState = this.providerClient.getInfoProviderByState(state);
		
		log.info("placing an order");
		InfoOrderDTO order = this.providerClient.placeOrder(purchase.getItems());
		
		purchaseSave.setState(PurchaseState.ORDER_REQUESTED);
		purchaseSave.setOrderId(order.getId());
		purchaseSave.setPreparationTime(order.getPreparationTime());
		this.purchaseRepository.save(purchaseSave);
		
		
		// Step 2 - Information Voucher/Delivery
		log.info("generating voucher");
		InfoDeliveryDTO infoDeliveryDTO = new InfoDeliveryDTO(
				order.getId(),
				LocalDate.now().plusDays(order.getPreparationTime()),
				infoProviderByState.getAddress(),
				purchase.getAddress().toString());
		
		VoucherDTO voucher = this.voucherClient.bookDelivery(infoDeliveryDTO);
		purchaseSave.setState(PurchaseState.RESERVE_DELIVERED);
		purchaseSave.setDeliveryDate(voucher.getDeliveryForecast());
		purchaseSave.setVoucher(voucher.getNumber());
		this.purchaseRepository.save(purchaseSave);
		
		return purchaseSave;
	}
	
	// Method for Fallback and Circuit Braker
	public Purchase makePurchaseFallback(PurchaseDTO purchase) {
		
		// Returns purchase with last status captured by fallback 
		if (purchase.getPurchaseId() != null) {
			return this.purchaseRepository.findById(purchase.getPurchaseId()).get();
		}
		
		Purchase purchaseFallback = new Purchase();
		purchaseFallback.setDestinationAddress(purchase.getAddress().toString());
		return purchaseFallback;
	}

	
}
