package com.practiceroom.profissionais.dto.contato;

import java.io.Serializable;

import com.practiceroom.profissionais.model.Profissional;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe DTO utilizada para entrada do cadastro e alteração de um contato.
 * @author Willian Robert Scabora
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContatoAtualizacaoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String nome;
	
	@NotNull
	private String contato;
	
	@NotNull
	private Profissional profissional;

}
