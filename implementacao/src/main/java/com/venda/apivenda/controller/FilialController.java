package com.venda.apivenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import com.venda.apivenda.model.Usuario;
import com.venda.apivenda.repository.FilialRepository;
import com.venda.apivenda.repository.UsuarioRepository;

@Controller
public class FilialController {

	@Autowired
	FilialRepository filialRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@RequestMapping(value = "/cadastrarFilial", method = RequestMethod.GET)
	public ModelAndView form() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails)
		  username = ((UserDetails)principal).getUsername();
		else
		  username = principal.toString();
		
		Usuario usuario = usuarioRepository.findByLogin(username);
		ModelAndView model = new ModelAndView("/filial/formFilial");
		
		model.addObject("usuario", usuario);
		
		return model;
	}
	
	@RequestMapping(value = "/cadastrarFilial", method = RequestMethod.POST)
	public String form(Filial filial) {
		filialRepository.save(filial);
		
		return "redirect:/cadastrarFilial";
	}
	
	@RequestMapping(value = "/filiais")
	public ModelAndView listaFiliais() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails)
		  username = ((UserDetails)principal).getUsername();
		else
		  username = principal.toString();
		
		Usuario usuario = usuarioRepository.findByLogin(username);		
		ModelAndView model = new ModelAndView("filial/listarFiliais");
		Iterable<Filial> filiais = filialRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
		
		model.addObject("filiais", filiais);
		model.addObject("usuario", usuario);
		
		return model;
	}
	
	@RequestMapping(value = "/editarFilial/{id}")
	public ModelAndView editarFilial(@PathVariable("id") Long id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails)
		  username = ((UserDetails)principal).getUsername();
		else
		  username = principal.toString();
		
		Usuario usuario = usuarioRepository.findByLogin(username);
		
		ModelAndView model = new ModelAndView("filial/editarFilial");
		Filial filial = filialRepository.findById(id).orElse(null);

		model.addObject("filial", filial);
		model.addObject("usuario", usuario);
		
		return model;
		
	}
	
    @PostMapping("atualizarFilial")
    public String atualizarFilial(@ModelAttribute Filial filial) {
        filialRepository.save(filial);

        return "redirect:/filiais";
    }
	
	@RequestMapping("/excluirFilial/{id}")
	public String excluirFilial(@PathVariable("id") Long id) {
		filialRepository.deleteById(id);
		
		return "redirect:/filiais";
	}
	
}
