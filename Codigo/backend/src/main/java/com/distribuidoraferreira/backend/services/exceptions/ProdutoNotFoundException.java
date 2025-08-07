package com.distribuidoraferreira.backend.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProdutoNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Produto nao encontrado!";

    public ProdutoNotFoundException() {
        super(MESSAGE);
    }
}
