package com.distribuidoraferreira.backend.enums;

import lombok.Getter;

@Getter
public enum StatusCaixa {
    ABERTO("ABERTO"),
    FECHADO("FECHADO");

    private final String status;

    StatusCaixa(String status) { this.status = status; }
}
