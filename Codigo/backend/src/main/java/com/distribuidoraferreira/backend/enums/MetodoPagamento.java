package com.distribuidoraferreira.backend.enums;

import lombok.Getter;

@Getter
public enum MetodoPagamento {
    PIX("PIX"),
    DEBITO("DEBITO"),
    CREDITO("CREDITO"),
    DINHEIRO("DINHEIRO");

    private final String metodo;

    MetodoPagamento(String metodo) {
        this.metodo = metodo;
    }

}
