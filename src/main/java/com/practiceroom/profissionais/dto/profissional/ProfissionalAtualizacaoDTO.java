package com.practiceroom.profissionais.dto.profissional;

import java.time.LocalDate;

import com.practiceroom.profissionais.enumerator.CargoEnum;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe DTO utilizada para entrada do cadastro e alteração de um profissional.
 * @author Willian Robert Scabora
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfissionalAtualizacaoDTO {
	
    @NotNull
    private String nome;
    
    @NotNull
    private CargoEnum cargo;
    
    @NotNull
    private LocalDate nascimento;

}
