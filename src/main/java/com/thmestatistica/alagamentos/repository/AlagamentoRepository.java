package com.thmestatistica.alagamentos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.thmestatistica.alagamentos.model.Alagamento;

public interface AlagamentoRepository extends JpaRepository<Alagamento, Long>{
	
	@Override
	Page<Alagamento> findAll(Pageable paginacao);
	
}
