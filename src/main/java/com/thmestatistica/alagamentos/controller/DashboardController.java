package com.thmestatistica.alagamentos.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thmestatistica.alagamentos.dto.NovaFotoDto;
import com.thmestatistica.alagamentos.model.Alagamento;
import com.thmestatistica.alagamentos.model.Foto;
import com.thmestatistica.alagamentos.model.Usuario;
import com.thmestatistica.alagamentos.service.AlagamentoService;


@Controller
public class DashboardController {
	
	@Autowired
	AlagamentoService alagamentoService;
	
	@GetMapping("/")
	public String dashboard(Model model, Integer page, Integer filter) {
		
		if(page == null) {
			page = 0;
		}
		
		//Filtros (criar uma classe de filtros)
		
		//exemplo de filtros multiplos
		//sort = Sort.by(Sort.Order.desc("avaliacao"), Sort.Order.asc("nomeDoUsuario"));
		//-----------------------------
		
		Sort sort = null;
		
		if(filter == null || filter > 4) {
			filter = 1;
		}
		
		if(filter == 1) {
			sort = Sort.by(Sort.Order.desc("id"));
		}
		
		if(filter == 2) {
			sort = Sort.by(Sort.Order.asc("id"));
		}
		
		if(filter == 3) {
			sort = Sort.by(Sort.Order.desc("data"));
		}
		
		if(filter == 4) {
			sort = Sort.by(Sort.Order.asc("data"));
		}
		
		//Paginacao
		PageRequest paginacao = PageRequest.of(page, 4, sort);
		
		//Busca no repositorio
		Page<Alagamento> alagamentos = alagamentoService.findAll(paginacao);
		
		//Autenticacao e HTML
		Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("alagamentos", alagamentos);
		model.addAttribute("usuarioLogado", usuarioLogado);
		model.addAttribute("filter", filter);
		return "dashboard";
	}
	
	@RequestMapping(value="/registraralagamento", method=RequestMethod.GET)
	public String registrar(Alagamento alagamento) {
		
		return "registraralagamento";
	}
	
	@RequestMapping(value="/topico/novo", method=RequestMethod.POST)
	public String novo(@Valid Alagamento alagamento, BindingResult result) {
		
		alagamentoService.create(alagamento, result);
		return "redirect:/";
	
	}
	
	@RequestMapping(value="/topico/adicionarfoto/{idDoTopico}", method=RequestMethod.GET)
	public String adicionarFoto(@PathVariable Long idDoTopico, Model model) {
		
		NovaFotoDto novaFotoDto = new NovaFotoDto();
		novaFotoDto.setAlagamento(idDoTopico);
		
		model.addAttribute("novafoto", novaFotoDto);
		
		
		return "adicionarfoto";
	}
	
	@RequestMapping(value="/topico/novafoto", method=RequestMethod.POST)
	public String novaFoto(NovaFotoDto novaFotoDto) {
		
		
		alagamentoService.salvarFoto(novaFotoDto.getUrl(), novaFotoDto.getAlagamento());
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/topico/verfotos/{idDoTopico}", method=RequestMethod.GET)
	public String verFotos(@PathVariable Long idDoTopico, Model model) {
		
		List<String> listaDeFotos = alagamentoService.pegarFotos(idDoTopico);
		
		
		model.addAttribute("listadefotos", listaDeFotos);
		
		
		return "verfotos";
	}
}
