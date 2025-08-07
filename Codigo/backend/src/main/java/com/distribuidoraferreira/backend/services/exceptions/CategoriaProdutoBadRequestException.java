package com.distribuidoraferreira.backend.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoriaProdutoBadRequestException extends RuntimeException {
    private static final String MESSAGE = "Atributo categoria invalido!";

    public CategoriaProdutoBadRequestException() {
        super(MESSAGE);
    }
}
