package com.thmestatistica.alagamentos.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.lang.NonNull;

import com.thmestatistica.alagamentos.enums.Classificacao;
import com.thmestatistica.alagamentos.enums.Intensidade;

@Entity
public class Alagamento {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	private LocalDateTime data;
	
	@NonNull
	private String localizacao;
	
	@NonNull
	private Intensidade intensidade;
	
	@NonNull
	private Classificacao classificacao;
	
	@OneToMany
	private List<Foto> registros = new ArrayList<>();

	
	//Getters e Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public String getLocalizacao() {
		return localizacao;
	}
	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
	public Intensidade getIntensidade() {
		return intensidade;
	}
	public void setIntensidade(Intensidade intensidade) {
		this.intensidade = intensidade;
	}
	public Classificacao getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(Classificacao classificacao) {
		this.classificacao = classificacao;
	}
	public List<Foto> getRegistros() {
		return registros;
	}
	public void setRegistros(List<Foto> registros) {
		this.registros = registros;
	}
}
