package br.com.microservice.loja.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.microservice.loja.entities.Purchase;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
	
	
	
}
