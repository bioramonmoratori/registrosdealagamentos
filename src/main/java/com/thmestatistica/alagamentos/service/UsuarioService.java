package com.thmestatistica.alagamentos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.thmestatistica.alagamentos.model.Perfil;
import com.thmestatistica.alagamentos.model.Usuario;
import com.thmestatistica.alagamentos.repository.PerfilRepository;
import com.thmestatistica.alagamentos.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	PerfilRepository perfilRepository;
	
	public String create(Usuario usuario, BindingResult result) {
		
		//Confere se a validacao promovida pelo bean validation veio com algum erro
		if(result.hasErrors()) {
			System.out.println("Nao realizou o registro pq deu algum erro");
			return "registro";
		}
		
		//Checando se o usuario ja existe
		List<Usuario> listaDeUsuarios = usuarioRepository.findAll();
		
		for(Usuario usuarioDaLista : listaDeUsuarios)
		{
		     String nomeDaLista = usuarioDaLista.getLogin();
		     if(nomeDaLista.equals(usuario.getLogin())) {
		    	 System.out.println("Nao realizou o registro porque o usuario " + usuario.getLogin() + " ja existe");
		    	 return "registro";
		     }
		}
		
		//Encriptografando senha
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());
		usuario.setSenha(senhaCriptografada);
		
		//Setando perfil
		Optional<Perfil> perfilOptional = perfilRepository.findByNome("ROLE_USUARIO");
		Perfil perfil = perfilOptional.get();
		usuario.adicionarPerfil(perfil);
		
		
		usuarioRepository.save(usuario);
		
		return "redirect:/login";
		
	}
	
}
