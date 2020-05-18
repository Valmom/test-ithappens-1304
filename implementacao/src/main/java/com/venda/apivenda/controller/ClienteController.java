package com.venda.apivenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.venda.apivenda.model.Cliente;
import com.venda.apivenda.model.Usuario;
import com.venda.apivenda.repository.ClienteRepository;
import com.venda.apivenda.repository.UsuarioRepository;

@Controller
public class ClienteController {

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@RequestMapping(value = "/cadastrarCliente", method = RequestMethod.GET) 
	public ModelAndView form() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails)
		  username = ((UserDetails)principal).getUsername();
		else
		  username = principal.toString();
		
		Usuario usuario = usuarioRepository.findByLogin(username);
		ModelAndView model = new ModelAndView("/cliente/formCliente");
		
		model.addObject("usuario", usuario);
		
		return model;
	}
	
	@RequestMapping(value = "/cadastrarCliente", method = RequestMethod.POST)
	public ModelAndView form(Cliente cliente, BindingResult result, RedirectAttributes atributtes) {
		clienteRepository.save(cliente);
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails)
		  username = ((UserDetails)principal).getUsername();
		else
		  username = principal.toString();
		
		Usuario usuario = usuarioRepository.findByLogin(username);
		ModelAndView model = new ModelAndView("redirect:/cadastrarCliente");
		
		model.addObject("usuario", usuario);
		atributtes.addFlashAttribute("mensagem", "CLiente cadastrado com sucesso.");
		
		return model;

	}
	
	@RequestMapping(value = "/clientes")
	public ModelAndView listaClientes() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails)
		  username = ((UserDetails)principal).getUsername();
		else
		  username = principal.toString();
		
		Usuario usuario = usuarioRepository.findByLogin(username);
		ModelAndView model = new ModelAndView("cliente/listarClientes");
		
		Iterable<Cliente> clientes = clienteRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
		
		model.addObject("clientes", clientes);
		model.addObject("usuario", usuario);
		
		return model;
	}
	
	@RequestMapping(value = "/editarCliente/{id}")
	public ModelAndView editarFilial(@PathVariable Long id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails)
		  username = ((UserDetails)principal).getUsername();
		else
		  username = principal.toString();
		
		Usuario usuario = usuarioRepository.findByLogin(username);
		ModelAndView model = new ModelAndView("cliente/editarCliente");
				
		Cliente cliente = clienteRepository.findById(id).orElse(null);
		
		model.addObject("usuario", usuario);
		model.addObject("cliente", cliente);
		
		return model;
	}
	
	@PostMapping("atualizarCliente")
	public String atualizaCliente(@ModelAttribute Cliente cliente) {
		clienteRepository.save(cliente);
		
		return "redirect:/clientes";
	}
	
	@RequestMapping(value = "/excluirCliente/{id}")
	public String excluirCliente(@PathVariable Long id) {
		clienteRepository.deleteById(id);
		
		return "redirect:/clientes";
	}
	
}
