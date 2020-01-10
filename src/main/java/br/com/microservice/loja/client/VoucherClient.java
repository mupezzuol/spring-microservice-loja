package br.com.microservice.loja.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.microservice.loja.model.dto.InfoDeliveryDTO;
import br.com.microservice.loja.model.dto.VoucherDTO;

@FeignClient("transportador") //name-id that eureka server
public interface VoucherClient {
	
	@PostMapping("/delivery")
	public VoucherDTO bookDelivery(@RequestBody InfoDeliveryDTO infoDeliveryDTO);

}
