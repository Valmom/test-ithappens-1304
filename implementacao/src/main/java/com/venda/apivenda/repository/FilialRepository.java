package com.venda.apivenda.repository;

import org.springframework.data.jpa.repository.*;

import com.venda.apivenda.model.Filial;

public interface FilialRepository extends JpaRepository<Filial, Long>{

	Filial findById(long id);
}
