package br.com.microservice.loja.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class PurchaseItemDTO {

	private long id;
	private int amount;
	
}
