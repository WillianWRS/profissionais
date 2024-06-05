package com.practiceroom.profissionais.util;

import com.practiceroom.profissionais.dto.contato.ContatoAtualizacaoDTO;
import com.practiceroom.profissionais.dto.profissional.ProfissionalAtualizacaoDTO;
import com.practiceroom.profissionais.model.Contato;
import com.practiceroom.profissionais.model.Profissional;

/**
 * Classe utilizada para disponibilizar métodos úteis para outras áreas da aplicação.
 * @author Willian Robert Scabora
 */
public class Utils {
	
	public static Contato atualizarContato(Contato contatoAntigo, ContatoAtualizacaoDTO contatoNovo) {
		Contato contatoAtualizado = contatoAntigo;
		if (contatoNovo.getNome() != null) {
			contatoAtualizado.setNome(contatoNovo.getNome());
		}
		if (contatoNovo.getContato() != null) {
			contatoAtualizado.setContato(contatoNovo.getContato());
		}
		return contatoAtualizado;
	}
	
	public static Profissional atualizarProfissional(Profissional profissionalAntigo,
			ProfissionalAtualizacaoDTO profissionalNovo) {
		Profissional profissionalAtualizado = profissionalAntigo;
		if (profissionalNovo.getNome() != null) {
			profissionalAtualizado.setNome(profissionalNovo.getNome());
		}

		if (profissionalNovo.getCargo() != null) {
			profissionalAtualizado.setCargo(profissionalNovo.getCargo());
		}

		if (profissionalNovo.getNascimento() != null) {
			profissionalAtualizado.setNascimento(profissionalNovo.getNascimento());
		}
		return profissionalAtualizado;
	}

}
