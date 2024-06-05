package com.practiceroom.profissionais.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.practiceroom.profissionais.dto.contato.ContatoDTO;
import com.practiceroom.profissionais.dto.profissional.ProfissionalAtualizacaoDTO;
import com.practiceroom.profissionais.dto.profissional.ProfissionalConsultaDTO;
import com.practiceroom.profissionais.dto.profissional.ProfissionalDTO;
import com.practiceroom.profissionais.enumerator.CargoEnum;
import com.practiceroom.profissionais.enumerator.StatusEnum;
import com.practiceroom.profissionais.exception.DataNotFoundException;
import com.practiceroom.profissionais.model.Profissional;
import com.practiceroom.profissionais.repository.ProfissionalRepository;
import com.practiceroom.profissionais.service.ContatoService;
import com.practiceroom.profissionais.service.ProfissionalService;

@ExtendWith(MockitoExtension.class)
public class ProfissionalServiceTest {

	@InjectMocks
	private ProfissionalService profissionalService;
	
	@Mock
	private ContatoService contatoService;
	
	@Mock
	private ProfissionalRepository profissionalRepository;
	
	ProfissionalDTO profissionalDTO;

	@BeforeEach
	public void setUp() {
		profissionalDTO = new ProfissionalDTO(1L, "Maria", CargoEnum.Desenvolvedor, LocalDate.now());
	}
	
	@Test
	public void quandoBuscarProfissionaisPorTextoECamposComSucesso() {
	    String q = "";
	    List<String> fields = Arrays.asList("nome", "contatos");

	    List<ProfissionalConsultaDTO> profissionaisMock = new ArrayList<ProfissionalConsultaDTO>();
	    profissionaisMock.add(new ProfissionalConsultaDTO(1L, "Maria", CargoEnum.Desenvolvedor, LocalDate.now(), LocalDate.now()));
	    when(profissionalRepository.findAllByQAndCampos(q, fields)).thenReturn(profissionaisMock);
	    
	    List<ContatoDTO> contatosMock = new ArrayList<>();
	    contatosMock.add(new ContatoDTO(1L, "Celular", "18996585441", LocalDate.now()));
	    when(contatoService.buscarContatosPorIdProfissional(1L)).thenReturn(contatosMock);

	    List<ProfissionalConsultaDTO> profissionaisDTO = profissionalService.buscar(q, fields);

	    assertFalse(profissionaisDTO.isEmpty());
	    for (ProfissionalConsultaDTO profissional : profissionaisDTO) {
	        assertFalse(profissional.getContatos().isEmpty());
	    }
	}
	
	@Test
	public void quandoBuscarProfissionaisPorTextoComSucesso() {
	    String q = "Maria";

	    List<ProfissionalConsultaDTO> profissionaisMock = new ArrayList<ProfissionalConsultaDTO>();
	    profissionaisMock.add(new ProfissionalConsultaDTO(1L, "Maria", CargoEnum.Desenvolvedor, LocalDate.now(), LocalDate.now()));
	    when(profissionalRepository.findAllByQ(q)).thenReturn(profissionaisMock);

	    List<ProfissionalConsultaDTO> profissionaisDTO = profissionalService.buscar(q, null);

	    assertFalse(profissionaisDTO.isEmpty());
	}
	
	@Test
	public void quandoBuscarProfissionaisPorTextoSemSucesso() {
	    String q = "Silvia";

	    List<ProfissionalConsultaDTO> profissionaisMock = new ArrayList<ProfissionalConsultaDTO>();
	    profissionaisMock.add(new ProfissionalConsultaDTO(1L, "Maria", CargoEnum.Desenvolvedor, LocalDate.now(), LocalDate.now()));
	    when(profissionalRepository.findAllByQ(q)).thenReturn(new ArrayList<ProfissionalConsultaDTO>());

	    assertThrows(DataNotFoundException.class, () -> {
	        profissionalService.buscar(q, null);
	    });
	}

	@Test
	public void quandoBuscarProfissionalPorIdComSucesso() {
		when(profissionalRepository.existsAtivoById(profissionalDTO.getId())).thenReturn(true);
		when(profissionalRepository.findByProfissionalId(profissionalDTO.getId())).thenReturn(profissionalDTO);
		
		ProfissionalDTO profissional = profissionalService.buscarPorId(profissionalDTO.getId());
		
		assertEquals(profissionalDTO, profissional);
	}
	
	@Test
	public void quandoBuscarProfissionalPorIdSemSucesso() {
	    when(profissionalRepository.existsAtivoById(profissionalDTO.getId())).thenReturn(false);

	    assertThrows(DataNotFoundException.class, () -> {
	        profissionalService.buscarPorId(profissionalDTO.getId());
	    });
	}
	
	@Test
	public void quandoCadastrarProfissionalComSucesso() {
	    ProfissionalAtualizacaoDTO profissionalDTO = new ProfissionalAtualizacaoDTO("Marcos", CargoEnum.Suporte, LocalDate.now());

	    Profissional profissionalSalvo = new Profissional(null, "Marcos", CargoEnum.Suporte, LocalDate.now(), LocalDate.now(), null, StatusEnum.Ativo, null);
	    when(profissionalRepository.save(any(Profissional.class))).thenReturn(profissionalSalvo);

	    ProfissionalDTO profissionalRetornado = profissionalService.cadastrar(profissionalDTO);

	    assertNotNull(profissionalRetornado);
	    assertEquals(profissionalSalvo.getId(), profissionalRetornado.getId());
	}
	
	@Test
	public void quandoAlterarProfissionalExistenteComSucesso() {
	    Long id = 1L;
	    ProfissionalAtualizacaoDTO profissionalDTO = new ProfissionalAtualizacaoDTO("Carla", null, null);

	    when(profissionalRepository.existsAtivoById(id)).thenReturn(true);
	    Profissional profissionalExistente = new Profissional(1L, "Maria", CargoEnum.Suporte, LocalDate.now(), LocalDate.now(), null, StatusEnum.Ativo, null);
	    when(profissionalRepository.findById(id)).thenReturn(Optional.of(profissionalExistente));
	    Profissional profissionalSalvo = new Profissional(1L, "Carla", CargoEnum.Suporte, LocalDate.now(), LocalDate.now(), null, StatusEnum.Ativo, null);
	    when(profissionalRepository.save(any(Profissional.class))).thenReturn(profissionalSalvo);

	    ProfissionalDTO profissionalRetornado = profissionalService.alterar(id, profissionalDTO);

	    assertNotNull(profissionalRetornado);
	    assertEquals(profissionalSalvo.getId(), profissionalRetornado.getId());
	}

	@Test
	public void quandoAlterarProfissionalSemSucesso() {
	    Long id = 1L;
	    ProfissionalAtualizacaoDTO profissionalDTO = new ProfissionalAtualizacaoDTO("Carla", null, null);

	    when(profissionalRepository.existsAtivoById(id)).thenReturn(false);

	    assertThrows(DataNotFoundException.class, () -> {
	        profissionalService.alterar(id, profissionalDTO);
	    });
	}
	
	@Test
	public void quandoExcluirProfissionalComSucesso() {
	    Long id = 1L;

	    when(profissionalRepository.existsAtivoById(id)).thenReturn(true);
	    Profissional profissionalExistente = new Profissional(1L, "Maria", CargoEnum.Suporte, LocalDate.now(), LocalDate.now(), null, StatusEnum.Ativo, null);
	    when(profissionalRepository.findById(id)).thenReturn(Optional.of(profissionalExistente));
	    when(profissionalRepository.save(any(Profissional.class))).then(AdditionalAnswers.returnsFirstArg());

	    profissionalService.excluir(id);

	    assertEquals(StatusEnum.Inativo, profissionalExistente.getStatus());
	    assertNotNull(profissionalExistente.getDataExclusao());
	}

	@Test
	public void quandoExcluirProfissionalSemSucesso() {
	    Long id = 1L;

	    when(profissionalRepository.existsAtivoById(id)).thenReturn(false);

	    assertThrows(DataNotFoundException.class, () -> {
	        profissionalService.excluir(id);
	    });
	}
	
	@Test
	public void quandoBuscarProfissionalPorQAndCamposComSucesso() {
	    String q = "";
	    List<String> fields = Arrays.asList("nome", "contatos");

	    List<ProfissionalConsultaDTO> profissionaisMock = new ArrayList<>();
	    profissionaisMock.add(new ProfissionalConsultaDTO(1L, "Maria", CargoEnum.Desenvolvedor, LocalDate.now(), LocalDate.now()));
	    when(profissionalService.buscarProfissionalPorQAndCampos(q, fields)).thenReturn(profissionaisMock);

	    List<ProfissionalConsultaDTO> profissionaisRetornados = profissionalService.buscar(q, fields);

	    assertEquals(profissionaisMock, profissionaisRetornados);
	}
	
	@Test
	public void quandoBuscarProfissionalPorContatoIdComSucesso() {
	    Long idContato = 1L;

	    ProfissionalDTO profissionalMock = new ProfissionalDTO(1L, "Maria", CargoEnum.Tester, LocalDate.now());
	    ContatoDTO contato = new ContatoDTO(1L, "Casa", "33223170", LocalDate.now());
	    List<ContatoDTO> contatos = Arrays.asList(contato);
	    profissionalMock.setContatos(contatos);
	    when(profissionalRepository.findByContatoId(idContato)).thenReturn(profissionalMock);

	    ProfissionalDTO profissionalRetornado = profissionalService.buscarProfissionalPorContatoId(idContato);

	    assertNotNull(profissionalRetornado);
	    assertEquals(profissionalMock.getId(), profissionalRetornado.getId());
	}

}
