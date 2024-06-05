package com.practiceroom.profissionais.dto.profissional;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.practiceroom.profissionais.dto.contato.ContatoDTO;
import com.practiceroom.profissionais.enumerator.CargoEnum;
import com.practiceroom.profissionais.model.Profissional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe DTO de profissional utilizada para retorno de cadastro, alteração e busca por ID.
 * @author Willian Robert Scabora
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfissionalDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String nome;

	private CargoEnum cargo;

	private LocalDate nascimento;

	private LocalDate dataCriacao;

	private List<ContatoDTO> contatos;

	public ProfissionalDTO(Long id, String nome, CargoEnum cargo, LocalDate nascimento, LocalDate dataCriacao) {
		this.id = id;
		this.nome = nome;
		this.cargo = cargo;
		this.nascimento = nascimento;
		this.dataCriacao = dataCriacao;
	}

	public ProfissionalDTO(Long id) {
		this.id = id;
	}

	public ProfissionalDTO(Profissional profissional) {
		this.id = profissional.getId();
		this.nome = profissional.getNome();
		this.cargo = profissional.getCargo();
		this.nascimento = profissional.getNascimento();
	}
	
	public ProfissionalDTO(Long id, String nome, CargoEnum cargo, LocalDate dataCriacao) {
		this.id = id;
		this.nome = nome;
		this.cargo = cargo;
		this.dataCriacao = dataCriacao;
	}

}
