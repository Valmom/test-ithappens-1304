package com.venda.apivenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venda.apivenda.model.Filial;
import com.venda.apivenda.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	Produto findById(long id);
	
	Iterable<Produto> findByFilial(Filial filial);
	
}
