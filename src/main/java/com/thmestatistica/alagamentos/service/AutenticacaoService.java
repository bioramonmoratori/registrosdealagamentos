package com.thmestatistica.alagamentos.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thmestatistica.alagamentos.model.Usuario;
import com.thmestatistica.alagamentos.repository.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService{
	
	final UsuarioRepository usuarioRepository;
	
    public AutenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Usuario> usuario = usuarioRepository.findByLogin(username);
		
		if(usuario.isPresent()) {
			System.out.println(usuario.get().getAuthorities());
			return usuario.get();
		} 
		
		throw new UsernameNotFoundException("Usuario Nao Encontrado");
	}
	
}
