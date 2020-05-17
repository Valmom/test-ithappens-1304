package com.venda.apivenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venda.apivenda.model.Filial;
import com.venda.apivenda.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByLogin(String login);
	
	Iterable<Usuario> findByFilial(Filial filial);
}
