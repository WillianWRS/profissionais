package com.practiceroom.profissionais.model;

import java.time.LocalDate;

import com.practiceroom.profissionais.dto.contato.ContatoAtualizacaoDTO;
import com.practiceroom.profissionais.dto.contato.ContatoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe de Entidade de Contato.
 * @author Willian Robert Scabora
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contatos")
@Entity
public class Contato {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String contato;
	
	@Column(name = "created_date")
	private LocalDate dataCriacao;
	
	@ManyToOne
	@JoinColumn(name = "profissional_id", referencedColumnName = "id")
	private Profissional profissional;
	
	public Contato(ContatoDTO contatoDTO) {
		this.nome = contatoDTO.getNome();
		this.contato = contatoDTO.getContato();
		this.profissional = new Profissional(contatoDTO.getProfissional().getId());
		this.dataCriacao = LocalDate.now();
	}

	public Contato(ContatoAtualizacaoDTO contatoDTO) {
		this.nome = contatoDTO.getNome();
		this.contato = contatoDTO.getContato();
		this.profissional = new Profissional(contatoDTO.getProfissional().getId());
		this.dataCriacao = LocalDate.now();
	}

}
