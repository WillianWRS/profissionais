package com.practiceroom.profissionais.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.practiceroom.profissionais.dto.contato.ContatoAtualizacaoDTO;
import com.practiceroom.profissionais.dto.contato.ContatoConsultaDTO;
import com.practiceroom.profissionais.dto.contato.ContatoDTO;
import com.practiceroom.profissionais.exception.DataNotFoundException;
import com.practiceroom.profissionais.model.Contato;
import com.practiceroom.profissionais.repository.ContatoRepository;
import com.practiceroom.profissionais.util.Utils;

import lombok.RequiredArgsConstructor;

/**
 * Classe de serviço de Contato onde as regras de negócio dos Endpoints são aplicadas.
 * @author Willian Robert Scabora
 */
@Service
@RequiredArgsConstructor
public class ContatoService {
	
	private final ContatoRepository contatoRepository;
	
	/**
	 * Retorna uma Lista de contatos, podendo ser filtrados por texto e por
	 * campos.
	 * 
	 * @param q
	 * @param fields
	 * @return List
	 * @author Willian Robert Scabora
	 */
	public List<ContatoConsultaDTO> buscar(String q, List<String> fields) {
		List<ContatoConsultaDTO> contatosDTO = new ArrayList<ContatoConsultaDTO>();
		if (null == fields) {
			contatosDTO = contatoRepository.findAllByQ(q);
		} else {
			contatosDTO = contatoRepository.findAllByQAndCampos(q, fields);
		}
		
		if (contatosDTO.isEmpty()) {
			throw new DataNotFoundException("Contato");
		}
		
		return contatosDTO;
	}
	
	public ContatoDTO buscarPorId(Long contatoId) {
		if (contatoRepository.existsAtivoById(contatoId)) {
			return contatoRepository.findByContatoId(contatoId);
		}
		
		throw new DataNotFoundException("Contato");
	}
	
	public ContatoDTO cadastrar(ContatoAtualizacaoDTO contatoDTO) {
		Contato contato = new Contato(contatoDTO);
		contatoRepository.save(contato);
		ContatoDTO contatoSalvo = new ContatoDTO(contato);
		
		return contatoSalvo;
	}
	
	public ContatoDTO alterar(Long id, ContatoAtualizacaoDTO contatoDTO) {
		if (contatoRepository.existsAtivoById(id)) {
			Contato contato = contatoRepository.findById(id).get();
			contato = Utils.atualizarContato(contato, contatoDTO);
			contatoRepository.save(contato);
			
			return new ContatoDTO(contato);
		}
		
		throw new DataNotFoundException("Contato");
	}
	
	public void excluir(Long id) {
		if (contatoRepository.existsAtivoById(id)) {
			contatoRepository.deleteById(id);
		} else {
			throw new DataNotFoundException("Contato");
		}
	}
	
	public List<ContatoDTO> buscarContatosPorIdProfissional(Long profissionalId) {
		return contatoRepository.findAllByProfissionalId(profissionalId);
	}

}
