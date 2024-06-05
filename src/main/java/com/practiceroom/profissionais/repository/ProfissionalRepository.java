package com.practiceroom.profissionais.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.practiceroom.profissionais.dto.profissional.ProfissionalConsultaDTO;
import com.practiceroom.profissionais.dto.profissional.ProfissionalDTO;
import com.practiceroom.profissionais.model.Profissional;

/**
 * Classe responsável pelo acesso ao repositório de Profissional no banco de dados.
 * @author Willian Robert Scabora
 */
@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

	/**
	 * Retorna uma lista de profissionais ativos filtrados por texto e por campos.
	 * @param q
	 * @param campos
	 * @return List
	 * @author Willian Robert Scabora
	 */
	@Query(value = " SELECT NEW com.practiceroom.profissionais.dto.profissional.ProfissionalConsultaDTO(p.id, "
	        + "         CASE WHEN 'nome' IN (:campos) THEN p.nome END, "
	        + "         CASE WHEN 'cargo' IN (:campos) THEN p.cargo END, "
	        + "         CASE WHEN 'nascimento' IN (:campos) THEN p.nascimento END, "
	        + "         CASE WHEN 'dataCriacao' IN (:campos) THEN p.dataCriacao END) "
	        + "  FROM Profissional p "
	        + " WHERE p.status = 'Ativo' AND ((LOWER(p.nome) LIKE CONCAT('%', LOWER(:q), '%') OR :q IS NULL) "
	        + " OR (LOWER(p.cargo) LIKE CONCAT('%', LOWER(:q), '%') OR :q IS NULL) "
	        + " OR (TO_CHAR(p.nascimento, 'YYYY-MM-DD') LIKE CONCAT('%', :q, '%') OR :q IS NULL) "
	        + " OR (TO_CHAR(p.dataCriacao, 'YYYY-MM-DD') LIKE CONCAT('%', :q, '%') OR :q IS NULL))")
	List<ProfissionalConsultaDTO> findAllByQAndCampos(@Param("q") String q, @Param("campos") List<String> campos);
	
	/**
	 * Retorna uma lista de profissionais ativos filtrados por texto.
	 * @param q
	 * @return List
	 * @author Willian Robert Scabora
	 */
	@Query(value = " SELECT NEW com.practiceroom.profissionais.dto.profissional.ProfissionalConsultaDTO(p.id, p.nome, p.cargo, "
			+ "p.nascimento, p.dataCriacao) "
			+ " FROM Profissional p "
	        + " WHERE p.status = 'Ativo' AND ("
	        + " (LOWER(p.nome) LIKE CONCAT('%', LOWER(:q), '%') OR :q IS NULL) "
	        + " OR (LOWER(p.cargo) LIKE CONCAT('%', LOWER(:q), '%') OR :q IS NULL) "
	        + " OR (TO_CHAR(p.nascimento, 'YYYY-MM-DD') LIKE CONCAT('%', :q, '%') OR :q IS NULL) "
	        + " OR (TO_CHAR(p.dataCriacao, 'YYYY-MM-DD') LIKE CONCAT('%', :q, '%') OR :q IS NULL))")
	List<ProfissionalConsultaDTO> findAllByQ(@Param("q") String q);
	
	@Query(value = " SELECT NEW com.practiceroom.profissionais.dto.profissional.ProfissionalDTO(p.id, p.nome, p.cargo,"
			+ " p.nascimento, p.dataCriacao) "
			+ " FROM Profissional p "
			+ " WHERE p.id = :profissionalId ")
	ProfissionalDTO findByProfissionalId(@Param("profissionalId") Long profissionalId);
	
	@Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Profissional p "
			+ " WHERE p.id = :profissionalId AND p.status = 'Ativo'")
	boolean existsAtivoById(@Param("profissionalId") Long profissionalId);
	
	@Query(value = " SELECT NEW com.practiceroom.profissionais.dto.profissional.ProfissionalDTO(p.id, p.nome, p.cargo, p.nascimento, p.dataCriacao)"
			+ " FROM Profissional p"
			+ " INNER JOIN Contato c on c.profissional.id = p.id "
			+ " WHERE c.profissional.id = p.id AND c.id = :contatoId ")
	ProfissionalDTO findByContatoId(@Param("contatoId") Long contatoId);

}
