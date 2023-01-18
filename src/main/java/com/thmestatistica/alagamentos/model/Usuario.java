package com.thmestatistica.alagamentos.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	private String login;
	
	@NonNull
	private String senha;
	
	@OneToMany
	private List<Alagamento> alagamentos = new ArrayList<>();
	
	//Anotacao para trazer as roles junto ao chamar o objeto
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Perfil> perfis = new ArrayList<>();
	
	
	//Getters e Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public List<Alagamento> getAlagamentosDoUsuario() {
		return alagamentos;
	}
	public void setAlagamentosDoUsuario(List<Alagamento> alagamentos) {
		this.alagamentos = alagamentos;
	}
	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}
	
	
	
	//--------------------------------------------
	
		//Spring Security
		

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return this.perfis;
		}
		@Override
		public String getPassword() {
			
			return this.senha;
		}
		@Override
		public String getUsername() {

			return this.login;
		}
		@Override
		public boolean isAccountNonExpired() {
			
			return true;
		}
		@Override
		public boolean isAccountNonLocked() {
			
			return true;
		}
		@Override
		public boolean isCredentialsNonExpired() {
			
			return true;
		}
		@Override
		public boolean isEnabled() {
			
			return true;
		}
		
		//Adicionar perfis
		public void adicionarPerfil(Perfil perfil) {
		    this.perfis.add(perfil);
		}


	
}
