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

import com.practiceroom.profissionais.dto.profissional.ProfissionalAtualizacaoDTO;
import com.practiceroom.profissionais.dto.profissional.ProfissionalDTO;
import com.practiceroom.profissionais.service.ProfissionalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Classe responsável pelos Endpoints disponíveis dos Profissionais e pela configuração do swagger em cada um deles.
 * @author Willian Robert Scabora
 */
@RestController
@RequestMapping(value = "/profissional", produces = {"application/json"})
@Tag(name = "Profissionais")
@RequiredArgsConstructor
public class ProfissionalController {
	
	private final ProfissionalService profissionalService;
	
	@GetMapping
	@Operation(summary = "Busca os profissionais filtrados por texto e por campo", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Profissional não encontrado"),
			@ApiResponse(responseCode = "400", description = "Dados da requisição inválidos")
	})
	public ResponseEntity<?> buscar(@Parameter(description = "Texto de pesquisa", allowEmptyValue = true) @RequestParam("q") String q, 
			@Parameter(description = "Lista de campos a serem retornados", required = false) @RequestParam(required = false) List<String> fields) {
		return ResponseEntity.ok(profissionalService.buscar(q, fields));
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Busca um profissional por ID", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Profissional não encontrado"),
			@ApiResponse(responseCode = "400", description = "Dados da requisição inválidos")
	})
	public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {
		return ResponseEntity.ok(profissionalService.buscarPorId(id));
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Cadastra um novo profissional", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Profissional cadastrado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados da requisição inválidos")
	})
	public ResponseEntity<?> cadastrar(@RequestBody @Valid ProfissionalAtualizacaoDTO profissionalDTO, UriComponentsBuilder uriBuilder) {
		ProfissionalDTO profissional = profissionalService.cadastrar(profissionalDTO);
		URI uri = uriBuilder.path("profissional/{id}").buildAndExpand(profissional.getId()).toUri();
		return ResponseEntity.created(uri).body(profissional);
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Altera um profissional existente", method = "PUT")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Alteração realizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Profissional não encontrado"),
			@ApiResponse(responseCode = "400", description = "Dados da requisição inválidos")
	})
	public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody ProfissionalAtualizacaoDTO profissionalDTO) {
		ProfissionalDTO profissionalAtualizado = profissionalService.alterar(id, profissionalDTO);
		return ResponseEntity.ok(profissionalAtualizado);      
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Exclui um profissional existente", method = "DELETE")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Profissional excluído com sucesso"),
			@ApiResponse(responseCode = "404", description = "Profissional não encontrado"),
			@ApiResponse(responseCode = "400", description = "Dados da requisição inválidos")
	})
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		profissionalService.excluir(id);
		return ResponseEntity.noContent().build();
	}

}
