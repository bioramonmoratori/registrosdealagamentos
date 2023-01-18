package com.thmestatistica.alagamentos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thmestatistica.alagamentos.model.Usuario;
import com.thmestatistica.alagamentos.service.UsuarioService;

@Controller
public class LoginController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@RequestMapping("/login")
	public String login() {
		
		return "login";
	}
	
	@RequestMapping(value="/registro", method=RequestMethod.GET)
	public String registrar(Usuario usuario) {
		return "registro";
	}
	
	@RequestMapping(value="/novo", method=RequestMethod.POST)
	public String novo(@Valid Usuario usuario, BindingResult result) {
		
		return usuarioService.create(usuario, result);
		
	}
}
