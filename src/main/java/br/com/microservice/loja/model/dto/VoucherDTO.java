package br.com.microservice.loja.model.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class VoucherDTO {
	
	private Long number;
	private LocalDate deliveryForecast;

}
