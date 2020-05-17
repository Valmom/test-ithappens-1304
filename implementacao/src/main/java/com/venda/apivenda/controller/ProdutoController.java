package com.venda.apivenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.venda.apivenda.model.Filial;
import com.venda.apivenda.model.Produto;
import com.venda.apivenda.model.Usuario;
import com.venda.apivenda.repository.FilialRepository;
import com.venda.apivenda.repository.ProdutoRepository;
import com.venda.apivenda.repository.UsuarioRepository;

@Controller
public class ProdutoController {

	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	FilialRepository filialRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@RequestMapping(value = "/cadastrarProduto/{id}", method = RequestMethod.GET)
	public ModelAndView form(@PathVariable Long id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails)
		  username = ((UserDetails)principal).getUsername();
		else
		  username = principal.toString();
		
		Usuario usuario = usuarioRepository.findByLogin(username);

		ModelAndView model = new ModelAndView("produto/formProduto");
		Filial filial = filialRepository.findById(id).orElse(null);
		
		model.addObject("usuario", usuario);
		model.addObject("filial", filial);
		
		return model;
	}
	
	@RequestMapping(value = "/cadastrarProduto/{id}", method = RequestMethod.POST)
	public String form(@PathVariable Long id, Produto produto) {
		Filial filial = filialRepository.findById(id).orElse(null);
		
		produto.setFilial(filial);
		
		produtoRepository.save(produto);
		
		return "redirect:/cadastrarProduto/{id}";
	}
	
	@RequestMapping(value = "/produtos/{id}")
	public ModelAndView listaProdutos(@PathVariable Long id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails)
		  username = ((UserDetails)principal).getUsername();
		else
		  username = principal.toString();
		
		Usuario usuario = usuarioRepository.findByLogin(username);

		ModelAndView model = new ModelAndView("produto/listarProdutos");
		
		Filial filial = filialRepository.findById(id).orElse(null);
		
		Iterable<Produto> produtos = produtoRepository.findByFilial(filial);
		
		model.addObject("produtos", produtos);
		model.addObject("filial", filial);
		model.addObject("usuario", usuario);
		
		return model;
	}
	
	@RequestMapping(value = "/editarProduto/{id}")
	public ModelAndView editarProduto(@PathVariable Long id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails)
		  username = ((UserDetails)principal).getUsername();
		else
		  username = principal.toString();
		
		Usuario usuario = usuarioRepository.findByLogin(username);		
		ModelAndView model = new ModelAndView("produto/editarProduto");
		Produto produto = produtoRepository.findById(id).orElse(null);
		
		model.addObject("produto", produto);
		model.addObject("usuario", usuario);
		
		return model;
	}
	
	@PostMapping("atualizarProduto/{id}")
	public String atualizarUsuario(@PathVariable Long id, @ModelAttribute Produto produto) {
		
		produtoRepository.save(produto);
		
		return "redirect:/produtos/{id}";
	}
	
	@RequestMapping(value = "/excluirProduto/{id}")
	public String excluirProduto(@PathVariable Long id) {
		Produto produto = produtoRepository.findById(id).orElse(null);
		Filial filial = produto.getFilial();
		
		produtoRepository.deleteById(id);
		
		return "redirect:/produtos/" + filial.getId();
	}
}
