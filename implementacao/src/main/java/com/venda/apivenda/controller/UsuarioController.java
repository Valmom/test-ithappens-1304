package com.venda.apivenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.venda.apivenda.model.Filial;
import com.venda.apivenda.model.Usuario;
import com.venda.apivenda.repository.FilialRepository;
import com.venda.apivenda.repository.UsuarioRepository;

@Controller
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	FilialRepository filialRepository;
	
	@RequestMapping(value = "/cadastrarUsuario/{id}", method = RequestMethod.GET)
	public ModelAndView form(@PathVariable Long id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails)
		  username = ((UserDetails)principal).getUsername();
		else
		  username = principal.toString();
		
		ModelAndView model = new ModelAndView("usuario/formUsuario");
		Filial filial = filialRepository.findById(id).orElse(null);
		Usuario usuario = usuarioRepository.findByLogin(username);
		
		model.addObject("filial", filial);
		model.addObject("usuario", usuario);

		return model;
	}
	
	@RequestMapping(value = "/cadastrarUsuario/{id}", method = RequestMethod.POST)
	public ModelAndView form(@PathVariable Long id, Usuario usuario) {
		String senha = usuario.getSenha();
		Filial filial = filialRepository.findById(id).orElse(null);
		usuario.setFilial(filial);
		usuario.setSenha(new BCryptPasswordEncoder().encode(senha));
		ModelAndView model = new ModelAndView("redirect:/cadastrarUsuario/{id}");
		usuarioRepository.save(usuario);
		
		model.addObject("usuario", usuario);
		return model;
	}
	
	@RequestMapping(value = "/usuarios/{id}")
	public ModelAndView listaUsuarios(@PathVariable Long id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails)
		  username = ((UserDetails)principal).getUsername();
		else
		  username = principal.toString();
	
		Usuario usuario = usuarioRepository.findByLogin(username);
		Filial filial = filialRepository.findById(id).orElse(null);
		ModelAndView model = new ModelAndView("usuario/listarUsuarios");
		Iterable<Usuario> usuarios = usuarioRepository.findByFilial(filial);
		
		model.addObject("usuarios", usuarios);
		model.addObject("filial", filial);
		model.addObject("usuario", usuario);
		
		return model;
	}
	
	@RequestMapping(value = "/editarUsuario/{id}")
	public ModelAndView editarUsuario(@PathVariable Long id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails)
		  username = ((UserDetails)principal).getUsername();
		else
		  username = principal.toString();
	
		Usuario user = usuarioRepository.findByLogin(username);		
		ModelAndView model = new ModelAndView("usuario/editarUsuario");
		Usuario usuario = usuarioRepository.findById(id).orElse(null);
		
		model.addObject("usuario", usuario);
		model.addObject("user", user);
		
		return model;		
	}
	
	@PostMapping("atualizarUsuario/{id}")
	public String atualizarUsuario(@PathVariable Long id, @ModelAttribute Usuario usuario) {
		usuarioRepository.save(usuario);
		
		return "redirect:/usuarios/{id}";
	}
	
	@RequestMapping(value = "/excluirUsuario/{id}")
	public String excluirUsuario(@PathVariable Long id) {
		Usuario usuario = usuarioRepository.findById(id).orElse(null);
		Filial filial = usuario.getFilial();
		usuarioRepository.deleteById(id);
		
		return "redirect:/usuarios/" + filial.getId();
	}
}
