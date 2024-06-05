package com.practiceroom.profissionais.model;

import java.time.LocalDate;
import java.util.List;

import com.practiceroom.profissionais.dto.profissional.ProfissionalAtualizacaoDTO;
import com.practiceroom.profissionais.enumerator.CargoEnum;
import com.practiceroom.profissionais.enumerator.StatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe de entidade de Profissional.
 * @author Willian Robert Scabora
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profissionais")
@Entity
public class Profissional {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	@Enumerated(EnumType.STRING)
	private CargoEnum cargo;
	
	private LocalDate nascimento;
	
	@Column(name = "created_date")
	private LocalDate dataCriacao;
	
	@OneToMany(mappedBy = "contato", fetch = FetchType.LAZY)
	private List<Contato> contatos;
	
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	
	@Column(name = "deleted_date")
	private LocalDate dataExclusao;

	public Profissional(Long profissionalId) {
		this.id = profissionalId;
	}

	public Profissional(ProfissionalAtualizacaoDTO profissionalDTO) {
		this.nome = profissionalDTO.getNome();
		this.cargo = profissionalDTO.getCargo();
		this.nascimento = profissionalDTO.getNascimento();
		this.dataCriacao = LocalDate.now();
		this.status = StatusEnum.Ativo;
	}
	
}
