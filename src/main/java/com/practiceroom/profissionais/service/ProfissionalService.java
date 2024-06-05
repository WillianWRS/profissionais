package com.practiceroom.profissionais.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.practiceroom.profissionais.dto.profissional.ProfissionalAtualizacaoDTO;
import com.practiceroom.profissionais.dto.profissional.ProfissionalConsultaDTO;
import com.practiceroom.profissionais.dto.profissional.ProfissionalDTO;
import com.practiceroom.profissionais.enumerator.StatusEnum;
import com.practiceroom.profissionais.exception.DataNotFoundException;
import com.practiceroom.profissionais.model.Profissional;
import com.practiceroom.profissionais.repository.ProfissionalRepository;
import com.practiceroom.profissionais.util.Utils;

import lombok.RequiredArgsConstructor;

/**
 * Classe de serviço de Profissional onde as regras de negócio dos Endpoints são aplicadas.
 * @author Willian Robert Scabora
 */
@Service
@RequiredArgsConstructor
public class ProfissionalService {

	private final ProfissionalRepository profissionalRepository;

	private final ContatoService contatoService;

	/**
	 * Retorna uma Lista de profissionais, podendo ser filtrados por texto e por
	 * campos.
	 * 
	 * @param q
	 * @param fields
	 * @return List
	 * @author Willian Robert Scabora
	 */
	public List<ProfissionalConsultaDTO> buscar(String q, List<String> fields) {
		List<ProfissionalConsultaDTO> profissionaisDTO = new ArrayList<ProfissionalConsultaDTO>();
		boolean hasFields = !(fields == null);

		if (hasFields) {
			profissionaisDTO = profissionalRepository.findAllByQAndCampos(q, fields);
		} else {
			profissionaisDTO = profissionalRepository.findAllByQ(q);
		}

		if (profissionaisDTO.isEmpty()) {
			throw new DataNotFoundException("Profissional");
		}
		
		if (!hasFields || (fields != null && fields.contains("contatos"))) {
			for (ProfissionalConsultaDTO profissional : profissionaisDTO) {
				profissional.setContatos(contatoService.buscarContatosPorIdProfissional(profissional.getId()));
			}
		}

		return profissionaisDTO;
	}

	public ProfissionalDTO buscarPorId(Long profissionalId) {
		if (profissionalRepository.existsAtivoById(profissionalId)) {
			ProfissionalDTO profissionalDTO = profissionalRepository.findByProfissionalId(profissionalId);
			profissionalDTO.setContatos(contatoService.buscarContatosPorIdProfissional(profissionalId));
			return profissionalDTO;
		}

		throw new DataNotFoundException("Profissional");
	}

	public ProfissionalDTO cadastrar(ProfissionalAtualizacaoDTO profissionalDTO) {
		Profissional profissional = new Profissional(profissionalDTO);
		profissionalRepository.save(profissional);

		return new ProfissionalDTO(profissional);
	}

	public ProfissionalDTO alterar(Long id, ProfissionalAtualizacaoDTO profissionalDTO) {
		if (profissionalRepository.existsAtivoById(id)) {
			Profissional profissional = profissionalRepository.findById(id).get();
			profissional = Utils.atualizarProfissional(profissional, profissionalDTO);
			profissionalRepository.save(profissional);

			return new ProfissionalDTO(profissional);
		}

		throw new DataNotFoundException("Profissional");
	}

	public void excluir(Long id) {
		if (profissionalRepository.existsAtivoById(id)) {
			Profissional profissional = profissionalRepository.findById(id).get();
			profissional.setStatus(StatusEnum.Inativo);
			profissional.setDataExclusao(LocalDate.now());

			profissionalRepository.save(profissional);
		} else {
			throw new DataNotFoundException("Profissional");
		}
	}
	
	public List<ProfissionalConsultaDTO> buscarProfissionalPorQAndCampos(String q, List<String> fields) {
		return profissionalRepository.findAllByQAndCampos(q, fields);
	}

	public ProfissionalDTO buscarProfissionalPorContatoId(Long idContato) {
		return profissionalRepository.findByContatoId(idContato);
	}

}
