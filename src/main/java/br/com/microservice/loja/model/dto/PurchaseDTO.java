package br.com.microservice.loja.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class PurchaseDTO {

	@JsonIgnore
	private Long purchaseId;
	private List<PurchaseItemDTO> items;
	private AddressDTO address;

}
