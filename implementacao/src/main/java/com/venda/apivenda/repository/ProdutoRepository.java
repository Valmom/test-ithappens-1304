package com.venda.apivenda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.venda.apivenda.model.Filial;
import com.venda.apivenda.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	Produto findById(long id);
	
	Iterable<Produto> findByFilial(Filial filial);
	
	@Query("select p from Produto p where p.codigo=?1 or descricao like ?1")
	public List<Produto> pesquisarProduto(Object parametro);

}
