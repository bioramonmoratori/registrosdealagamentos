package com.thmestatistica.alagamentos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thmestatistica.alagamentos.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long>{
	
	Optional<Perfil> findByNome(String nome);
	
}
