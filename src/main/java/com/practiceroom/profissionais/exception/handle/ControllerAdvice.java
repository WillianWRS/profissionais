package com.practiceroom.profissionais.exception.handle;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.practiceroom.profissionais.exception.DataNotFoundException;

/**
 * Classe responsável por gerenciar o comportamento da aplicação durante a ocorrência de exceções.
 * @author Willian Robert Scabora
 */
@RestControllerAdvice
public class ControllerAdvice {
	
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected String handleException() {
        return "Erro interno";
    }
	
	@ExceptionHandler(DataNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleDataNotFoundException(DataNotFoundException exception) {
		return exception.getMessage();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public List<String> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
		return exception.getFieldErrors().stream().map(fieldError -> fieldError.getField() + " está inválido.")
			    .collect(Collectors.toList());
	}

}
