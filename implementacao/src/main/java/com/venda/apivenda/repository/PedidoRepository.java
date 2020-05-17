package com.venda.apivenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venda.apivenda.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	Pedido getById(long id);
}
