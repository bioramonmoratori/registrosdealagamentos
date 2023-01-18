package com.thmestatistica.alagamentos.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.thmestatistica.alagamentos.model.Alagamento;
import com.thmestatistica.alagamentos.model.Foto;
import com.thmestatistica.alagamentos.model.Usuario;
import com.thmestatistica.alagamentos.repository.AlagamentoRepository;
import com.thmestatistica.alagamentos.repository.FotoRepository;

@Service
public class AlagamentoService {
	
	@Autowired
	AlagamentoRepository alagamentoRepository;
	
	@Autowired
	FotoRepository fotoRepository;
	
	public Page<Alagamento> findAll(Pageable paginacao) {
		
		return alagamentoRepository.findAll(paginacao);
	}
	
	public String create(Alagamento alagamento, BindingResult result) {
		
		//Confere se a validacao promovida pelo bean validation veio com algum erro
		if(result.hasErrors()) {
			return "";
		}
		
		
		Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		alagamento.setData(LocalDateTime.now());
		alagamentoRepository.save(alagamento);
		return "redirect:/dashboard";
	}
	
	public Alagamento findById(Long idDoAlagamento) {
		return alagamentoRepository.findById(idDoAlagamento).get();
	}

	public void salvarFoto(String url, Long alagamentoId) {
		
		Foto foto = new Foto();
		foto.setUrl(url);
		fotoRepository.save(foto);
		
		Alagamento alagamento = alagamentoRepository.findById(alagamentoId).get();
		alagamento.getRegistros().add(foto);
		alagamentoRepository.save(alagamento);
		
	}

	public List<String> pegarFotos(Long idDoTopico) {
		
		List<String> listaDeFotos = new ArrayList<>();
		
		Alagamento alagamento = alagamentoRepository.findById(idDoTopico).get();
		
		List<Foto> listaDeObjetosFoto = alagamento.getRegistros();
		
		for (int i = 0; i < listaDeObjetosFoto.size(); i++) {
			
		    String urlFoto = listaDeObjetosFoto.get(i).getUrl();
		    listaDeFotos.add(urlFoto);
		}
		
		return listaDeFotos;
	}
	
}
