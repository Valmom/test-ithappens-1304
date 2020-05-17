package com.venda.apivenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venda.apivenda.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	Cliente getById(long id);
	
	Cliente findByDocumento(String documento);
}
