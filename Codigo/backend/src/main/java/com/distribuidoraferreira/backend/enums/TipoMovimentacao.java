package com.distribuidoraferreira.backend.enums;

import lombok.Getter;

@Getter
public enum TipoMovimentacao {
    ENTRADA("ENTRADA"),
    SAIDA("SAIDA"),
    PERDA("PERDA");

    private final String tipo;

    TipoMovimentacao(String tipo) {
        this.tipo = tipo;
    }

}
