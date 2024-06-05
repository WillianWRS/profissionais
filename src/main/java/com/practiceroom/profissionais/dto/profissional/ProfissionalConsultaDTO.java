package com.practiceroom.profissionais.dto.profissional;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.practiceroom.profissionais.dto.contato.ContatoDTO;
import com.practiceroom.profissionais.enumerator.CargoEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe DTO utilizada para consulta dinâmica de um profissional, não retorna os campos(nome, cargo, nascimento, dataCriacao, contatos) se forem nulos.
 * @author Willian Robert Scabora
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfissionalConsultaDTO {

	private Long id;

	private String nome;

	private CargoEnum cargo;

	private LocalDate nascimento;

	private LocalDate dataCriacao;

	private List<ContatoDTO> contatos;
	
	public ProfissionalConsultaDTO(Long id, String nome, CargoEnum cargo, LocalDate nascimento, LocalDate dataCriacao) {
		this.id = id;
		this.nome = nome;
		this.cargo = cargo;
		this.nascimento = nascimento;
		this.dataCriacao = dataCriacao;
	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String getNome() {
		return nome;
	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public CargoEnum getCargo() {
		return cargo;
	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public LocalDate getNascimento() {
		return nascimento;
	}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<ContatoDTO> getContatos() {
        return contatos;
    }

}
