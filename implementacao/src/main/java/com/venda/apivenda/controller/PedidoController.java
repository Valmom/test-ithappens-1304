package com.venda.apivenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.venda.apivenda.model.Cliente;
import com.venda.apivenda.model.Produto;
import com.venda.apivenda.model.Usuario;
import com.venda.apivenda.repository.ClienteRepository;
import com.venda.apivenda.repository.PedidoRepository;
import com.venda.apivenda.repository.ProdutoRepository;

@Controller
public class PedidoController {

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@RequestMapping(value = "/cadastrarPedido/{id}", method = RequestMethod.GET)
	public ModelAndView form(@PathVariable Long id) {
		ModelAndView model = new ModelAndView("/pedido/formPedido");
		
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
		Cliente cliente = clienteRepository.findById(id).orElse(null);
		Iterable<Produto> produtos = produtoRepository.findByFilial(usuario.getFilial());
		
		model.addObject("usuario", usuario);
		model.addObject("cliente", cliente);
		model.addObject("produtos", produtos);
		
		return model;
	}
	
	@RequestMapping(value = "/retornaClientes", method = RequestMethod.GET)
	public ModelAndView clientes() {
		ModelAndView model = new ModelAndView("pedido/listarClientes");
		
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();				
		Iterable<Cliente> clientes = clienteRepository.findAll();
		
		model.addObject("clientes", clientes);
		model.addObject("usuario", usuario);
		
		return model;
	}
	
	@RequestMapping(value = "/{id}/{codigo}", method = RequestMethod.GET)
	public ModelAndView produto(@PathVariable("id") Long id,@PathVariable("codigo") String codigo) {
		ModelAndView model = new ModelAndView("/pedido/formPedido");		
		List<Produto> produtos = produtoRepository.pesquisarProduto(codigo);
		
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();				
		Cliente cliente = clienteRepository.findById(id).orElse(null);
		
		model.addObject("cliente", cliente);
		model.addObject("usuario", usuario);		
		model.addObject("produto", produtos);
		
		return model;
	}
}
