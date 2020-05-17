package com.venda.apivenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.venda.apivenda.model.Cliente;
import com.venda.apivenda.model.Produto;
import com.venda.apivenda.model.Usuario;
import com.venda.apivenda.repository.ClienteRepository;
import com.venda.apivenda.repository.ProdutoRepository;
import com.venda.apivenda.repository.UsuarioRepository;

@Controller
public class PedidoController {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@RequestMapping(value = "/cadastrarPedido/{id}", method = RequestMethod.GET)
	public ModelAndView form(@PathVariable Long id) {
		ModelAndView model = new ModelAndView("/pedido/formPedido");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails)
		  username = ((UserDetails)principal).getUsername();
		else
		  username = principal.toString();
		
		Usuario usuario = usuarioRepository.findByLogin(username);		
		Cliente cliente = clienteRepository.findById(id).orElse(null);
		Iterable<Produto> produtos = produtoRepository.findByFilial(usuario.getFilial());
		
		model.addObject("usuario", usuario);
		model.addObject("cliente", cliente);
		model.addObject("produtos", produtos);
		
		return model;
	}
	
	@RequestMapping(value = "/retornaClientes", method = RequestMethod.GET)
	public ModelAndView clientes() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails)
		  username = ((UserDetails)principal).getUsername();
		else
		  username = principal.toString();
		
		Usuario usuario = usuarioRepository.findByLogin(username);		
		ModelAndView model = new ModelAndView("pedido/listarClientes");
		Iterable<Cliente> clientes = clienteRepository.findAll();
		
		model.addObject("clientes", clientes);
		model.addObject("usuario", usuario);
		
		return model;
	}
}
