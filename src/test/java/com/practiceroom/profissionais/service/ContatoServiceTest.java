package com.practiceroom.profissionais.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.practiceroom.profissionais.dto.contato.ContatoAtualizacaoDTO;
import com.practiceroom.profissionais.dto.contato.ContatoConsultaDTO;
import com.practiceroom.profissionais.dto.contato.ContatoDTO;
import com.practiceroom.profissionais.exception.DataNotFoundException;
import com.practiceroom.profissionais.model.Contato;
import com.practiceroom.profissionais.model.Profissional;
import com.practiceroom.profissionais.repository.ContatoRepository;
import com.practiceroom.profissionais.service.ContatoService;

@ExtendWith(MockitoExtension.class)
public class ContatoServiceTest {
	
	@InjectMocks
	private ContatoService contatoService;
	
	@Mock
	private ContatoRepository contatoRepository;
	
	@Test
	public void quandoBuscarContatosPorTextoECamposComSucesso() {
	    String q = "";
	    List<String> fields = Arrays.asList("nome", "contato");

	    List<ContatoConsultaDTO> contatosMock = new ArrayList<>();
	    contatosMock.add(new ContatoConsultaDTO(1L, "Casa", "33223170", LocalDate.now(), 1L));
	    when(contatoRepository.findAllByQAndCampos(q, fields)).thenReturn(contatosMock);

	    List<ContatoConsultaDTO> contatosDTO = contatoService.buscar(q, fields);
	    
	    assertFalse(contatosDTO.isEmpty());
	}

	@Test
	public void quandoBuscarContatosSemSucesso() {
	    String q = "Escritório";
	    List<String> fields = null;

	    when(contatoRepository.findAllByQ(q)).thenReturn(new ArrayList<>());

	    assertThrows(DataNotFoundException.class, () -> {
	        contatoService.buscar(q, fields);
	    });
	}
	
	@Test
	public void quandoBuscarContatoPorIdComSucesso() {
	    Long contatoId = 1L;

	    ContatoDTO contatoMock = new ContatoDTO(1L, "Casa", "33223170", LocalDate.now());
	    when(contatoRepository.existsAtivoById(contatoId)).thenReturn(true);
	    when(contatoRepository.findByContatoId(contatoId)).thenReturn(contatoMock);

	    ContatoDTO contatoRetornado = contatoService.buscarPorId(contatoId);

	    assertNotNull(contatoRetornado);
	    assertEquals(contatoMock.getId(), contatoRetornado.getId());
	}

	@Test
	public void quandoBuscarContatoPorIdSemSucesso() {
	    Long contatoId = 1L;

	    when(contatoRepository.existsAtivoById(contatoId)).thenReturn(false);

	    assertThrows(DataNotFoundException.class, () -> {
	        contatoService.buscarPorId(contatoId);
	    });
	}
	
	@Test
	public void quandoCadastrarContatoComSucesso() {
	    ContatoAtualizacaoDTO contatoDTO = new ContatoAtualizacaoDTO("Casa", "32211854", new Profissional(1L));

	    Contato contatoMock = new Contato(contatoDTO);
	    when(contatoRepository.save(any(Contato.class))).thenReturn(contatoMock);

	    ContatoDTO contatoRetornado = contatoService.cadastrar(contatoDTO);

	    assertNotNull(contatoRetornado);
	    assertEquals(contatoMock.getId(), contatoRetornado.getId());
	}
	
	@Test
	public void quandoAlterarContatoComSucesso() {
	    Long id = 1L;
	    ContatoAtualizacaoDTO contatoDTO = new ContatoAtualizacaoDTO("Casa", "32211854", new Profissional(1L));

	    Profissional profissional = new Profissional(1L);
	    Contato contatoExistente = new Contato(1L, "Celular", "1899654415", LocalDate.now(), profissional);
	    when(contatoRepository.existsAtivoById(id)).thenReturn(true);
	    when(contatoRepository.findById(id)).thenReturn(Optional.of(contatoExistente));
	    when(contatoRepository.save(any(Contato.class))).thenReturn(contatoExistente);

	    ContatoDTO contatoRetornado = contatoService.alterar(id, contatoDTO);

	    assertNotNull(contatoRetornado);
	    assertEquals(contatoExistente.getId(), contatoRetornado.getId());
	}

	@Test
	public void quandoAlterarContatoSemSucesso() {
	    Long id = 1L;
	    ContatoAtualizacaoDTO contatoDTO = new ContatoAtualizacaoDTO("Casa", "32211854", new Profissional(1L));

	    when(contatoRepository.existsAtivoById(id)).thenReturn(false);

	    assertThrows(DataNotFoundException.class, () -> {
	        contatoService.alterar(id, contatoDTO);
	    });
	}
	
	@Test
	public void quandoExcluirContatoComSucesso() {
	    Long id = 1L;

	    when(contatoRepository.existsAtivoById(id)).thenReturn(true);

	    contatoService.excluir(id);

	    verify(contatoRepository, times(1)).deleteById(id);
	}

	@Test
	public void quandoExcluirContatoSemSucesso() {
	    Long id = 1L;

	    when(contatoRepository.existsAtivoById(id)).thenReturn(false);

	    assertThrows(DataNotFoundException.class, () -> {
	        contatoService.excluir(id);
	    });
	}
	
	@Test
	public void quandoBuscarContatosPorIdProfissionalComSucesso() {
	    Long profissionalId = 1L;

	    List<ContatoDTO> contatosMock = new ArrayList<>();
	    contatosMock.add(new ContatoDTO(1L, "Casa", "33223170", LocalDate.now(), 1L));
	    contatosMock.add(new ContatoDTO(2L, "Escritório", "1898511742", LocalDate.now(), 1L));
	    when(contatoRepository.findAllByProfissionalId(profissionalId)).thenReturn(contatosMock);

	    List<ContatoDTO> contatosRetornados = contatoService.buscarContatosPorIdProfissional(profissionalId);

	    assertNotNull(contatosRetornados);
	    assertEquals(contatosMock.size(), contatosRetornados.size());
	    
	    for (int i = 0; i < contatosMock.size(); i++) {
	        ContatoDTO contatoMock = contatosMock.get(i);
	        ContatoDTO contatoRetornado = contatosRetornados.get(i);
	        assertEquals(contatoMock.getId(), contatoRetornado.getId());
	    }
	}

}
