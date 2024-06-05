package com.practiceroom.profissionais.exception;

/**
 * @author Willian Robert Scabora
 */
public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DataNotFoundException(String objeto) {
		super(objeto + " não encontrado.");
	}

}
