package br.com.microservice.loja.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Purchase {
	
	private Long orderId;
	private Integer preparationTime;
	private String destinationAddress;

}
