package br.com.microservice.loja.controller.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class PurchaseDTO {

	private List<PurchaseItemDTO> items;
	private AddressDTO address;

}
