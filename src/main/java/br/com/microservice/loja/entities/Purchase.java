package br.com.microservice.loja.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.microservice.loja.model.enums.PurchaseState;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long orderId;
	
	private Integer preparationTime;
	
	private String destinationAddress;
	
	private LocalDate deliveryDate;
	
	private Long voucher;
	
	@Enumerated(EnumType.STRING)
	private PurchaseState state;

}
