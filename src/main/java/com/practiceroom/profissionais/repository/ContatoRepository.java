package com.practiceroom.profissionais.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.practiceroom.profissionais.dto.contato.ContatoConsultaDTO;
import com.practiceroom.profissionais.dto.contato.ContatoDTO;
import com.practiceroom.profissionais.model.Contato;

/**
 * Classe responsável pelo acesso ao repositório do Contato no banco de dados.
 * @author Willian Robert Scabora
 */
@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

	@Query(value = " SELECT NEW com.practiceroom.profissionais.dto.contato.ContatoDTO(c.id, c.nome, c.contato, c.dataCriacao)"
			+ " FROM Contato c WHERE c.profissional.id = :profissionalId ")
	List<ContatoDTO> findAllByProfissionalId(@Param("profissionalId") Long profissionalId);
	
	/**
	 * Retorna uma lista de contatos filtrados por texto e por campos.
	 * @param q
	 * @param campos
	 * @return List
	 * @author Willian Robert Scabora
	 */
	@Query(value = " SELECT NEW com.practiceroom.profissionais.dto.contato.ContatoConsultaDTO(c.id, "
			+ "         CASE WHEN 'nome' IN (:campos) THEN c.nome END, "
			+ "         CASE WHEN 'contato' IN (:campos) THEN c.contato END, "
			+ "         CASE WHEN 'dataCriacao' IN (:campos) THEN c.dataCriacao END) "
			+ " FROM Contato c "
			+ " INNER JOIN Profissional p on p.id = c.profissional.id "
	        + " WHERE p.status = 'Ativo' AND ((LOWER(c.nome) LIKE CONCAT('%', LOWER(:q), '%') OR :q IS NULL) "
	        + " OR (LOWER(c.contato) LIKE CONCAT('%', LOWER(:q), '%') OR :q IS NULL) "
	        + " OR (TO_CHAR(c.dataCriacao, 'YYYY-MM-DD') LIKE CONCAT('%', :q, '%') OR :q IS NULL)) ")
	List<ContatoConsultaDTO> findAllByQAndCampos(@Param("q") String q, @Param("campos") List<String> campos);
	
	/**
	 * Retorna uma lista de profissionais filtrados por texto.
	 * @param q
	 * @return List
	 * @author Willian Robert Scabora
	 */
	@Query(value = " SELECT NEW com.practiceroom.profissionais.dto.contato.ContatoConsultaDTO(c.id, c.nome, c.contato, c.dataCriacao)"
			+ " FROM Contato c "
			+ " INNER JOIN Profissional p on p.id = c.profissional.id "
	        + " WHERE p.status = 'Ativo' AND ((LOWER(c.nome) LIKE CONCAT('%', LOWER(:q), '%') OR :q IS NULL) "
	        + " OR (LOWER(c.contato) LIKE CONCAT('%', LOWER(:q), '%') OR :q IS NULL) "
	        + " OR (TO_CHAR(c.dataCriacao, 'YYYY-MM-DD') LIKE CONCAT('%', :q, '%') OR :q IS NULL)) ")
	List<ContatoConsultaDTO> findAllByQ(@Param("q") String q);
	
	@Query(value = " SELECT NEW com.practiceroom.profissionais.dto.contato.ContatoDTO(c.id, c.nome, c.contato,"
			+ " c.dataCriacao, p.id ) "
			+ " FROM Contato c "
			+ " INNER JOIN Profissional p on p.id = c.profissional.id "
			+ " WHERE c.id = :contatoId ")
	ContatoDTO findByContatoId(Long contatoId);

	@Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Contato c "
			+ " INNER JOIN Profissional p on p.id = c.profissional.id "
			+ " WHERE c.id = :contatoId AND p.status = 'Ativo'")
	boolean existsAtivoById(Long contatoId);
	
	

}
