package com.distribuidoraferreira.backend.enums;

import lombok.Getter;

@Getter
public enum StatusVenda {
    PENDENTE("PENDENTE"),
    CONCLUIDA("CONCLUIDA");

    private final String status;

    StatusVenda(String status) {
        this.status = status;
    }

}
