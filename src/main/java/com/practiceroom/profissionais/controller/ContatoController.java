package com.practiceroom.profissionais.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.practiceroom.profissionais.dto.contato.ContatoAtualizacaoDTO;
import com.practiceroom.profissionais.dto.contato.ContatoDTO;
import com.practiceroom.profissionais.service.ContatoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Classe responsável pelos Endpoints disponíveis dos Contatos e pela configuração do swagger em cada um deles.
 * @author Willian Robert Scabora
 */
@RestController
@RequestMapping(value = "/contato", produces = {"application/json"})
@Tag(name = "Contatos")
@RequiredArgsConstructor
public class ContatoController {
	
	private final ContatoService contatoService;
	
	@GetMapping
	@Operation(summary = "Busca os contatos filtrados por texto e por campo", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Profissional não encontrado"),
			@ApiResponse(responseCode = "400", description = "Dados da requisição inválidos")
	})
	public ResponseEntity<?> buscar(@Parameter(description = "Texto de pesquisa", allowEmptyValue = true) @RequestParam("q") String q, 
			@Parameter(description = "Lista de campos a serem retornados", required = false) @RequestParam(required = false) List<String> fields) {
		return ResponseEntity.ok(contatoService.buscar(q, fields));
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Busca um contato por ID", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Contato não encontrado"),
			@ApiResponse(responseCode = "400", description = "Dados da requisição inválidos")
	})
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(contatoService.buscarPorId(id));
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Cadastra um novo contato", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Contato cadastrado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados da requisição inválidos")
	})
	public ResponseEntity<?> cadastrar(@RequestBody @Valid ContatoAtualizacaoDTO contatoDTO, UriComponentsBuilder uriBuilder) {
		ContatoDTO contato = contatoService.cadastrar(contatoDTO);
		URI uri = uriBuilder.path("contato/{id}").buildAndExpand(contato.getId()).toUri();
		return ResponseEntity.created(uri).body(contato);
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Altera um contato existente", method = "PUT")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Alteração realizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Contato não encontrado"),
			@ApiResponse(responseCode = "400", description = "Dados da requisição inválidos")
	})
	public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody ContatoAtualizacaoDTO contatoDTO) {
		ContatoDTO contatoAtualizado = contatoService.alterar(id, contatoDTO);
		return ResponseEntity.ok(contatoAtualizado);
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Exclui um contato existente", method = "DELETE")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Contato excluído com sucesso"),
			@ApiResponse(responseCode = "404", description = "Contato não encontrado"),
			@ApiResponse(responseCode = "400", description = "Dados da requisição inválidos")
	})
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		contatoService.excluir(id);
		return ResponseEntity.noContent().build();
	}

}
