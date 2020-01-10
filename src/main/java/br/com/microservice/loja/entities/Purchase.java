package br.com.microservice.loja.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity(name = "PurchaseEntity")
@Table(name = "tbl_purchase")
public class Purchase {
	
	@Id
	private Long orderId;
	private Integer preparationTime;
	private String destinationAddress;
	private LocalDate deliveryDate;
	private Long voucher;

}
