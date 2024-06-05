package com.practiceroom.profissionais.dto.contato;

import java.io.Serializable;
import java.time.LocalDate;

import com.practiceroom.profissionais.dto.profissional.ProfissionalDTO;
import com.practiceroom.profissionais.model.Contato;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe DTO de contato utilizada para retorno de cadastro, alteração e busca por ID.
 * @author Willian Robert Scabora
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContatoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String nome;
	
	private String contato;
	
	private LocalDate dataCriacao;
	
	private ProfissionalDTO profissional;
	
	public ContatoDTO(Long id, String nome, String contato, LocalDate dataCriacao) {
		this.id = id;
		this.nome = nome;
		this.contato = contato;
		this.dataCriacao = dataCriacao;
	}
	
	public ContatoDTO(Long id, String nome, String contato, LocalDate dataCriacao, Long profissionalId) {
		this.id = id;
		this.nome = nome;
		this.contato = contato;
		this.dataCriacao = dataCriacao;
		this.profissional = new ProfissionalDTO(profissionalId);
	}

	public ContatoDTO(Contato contatoAtualizado) {
		this.id = contatoAtualizado.getId();
		this.nome = contatoAtualizado.getNome();
		this.contato = contatoAtualizado.getContato();
		this.dataCriacao = contatoAtualizado.getDataCriacao();
		this.profissional = new ProfissionalDTO(contatoAtualizado.getProfissional());
	}
	
}
