package com.practiceroom.profissionais.dto.contato;

import java.io.Serializable;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.practiceroom.profissionais.dto.profissional.ProfissionalDTO;
import com.practiceroom.profissionais.model.Contato;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe DTO utilizada para consulta dinâmica de um contato, não retorna os campos(nome, contato, dataCriacao, profissional) se forem nulos.
 * @author Willian Robert Scabora
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContatoConsultaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String nome;
	
	private String contato;
	
	private LocalDate dataCriacao;
	
	private ProfissionalDTO profissional;
	
	public ContatoConsultaDTO(Long id, String nome, String contato, LocalDate dataCriacao) {
		this.id = id;
		this.nome = nome;
		this.contato = contato;
		this.dataCriacao = dataCriacao;
	}
	
	public ContatoConsultaDTO(Long id, String nome, String contato, LocalDate dataCriacao, Long profissionalId) {
		this.id = id;
		this.nome = nome;
		this.contato = contato;
		this.dataCriacao = dataCriacao;
		this.profissional = new ProfissionalDTO(profissionalId);
	}

	public ContatoConsultaDTO(Contato contatoAtualizado) {
		this.id = contatoAtualizado.getId();
		this.nome = contatoAtualizado.getNome();
		this.contato = contatoAtualizado.getContato();
		this.dataCriacao = contatoAtualizado.getDataCriacao();
		this.profissional = new ProfissionalDTO(contatoAtualizado.getProfissional());
	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String getNome() {
		return nome;
	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String getContato() {
		return contato;
	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public LocalDate getDataCriacao() {
		return dataCriacao;
	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public ProfissionalDTO getProfissional() {
		return profissional;
	}

}
