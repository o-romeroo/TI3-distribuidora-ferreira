package com.distribuidoraferreira.backend.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoriaNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Categoria nao encontrada!";
    
    public CategoriaNotFoundException() {
        super(MESSAGE);
    }
}