package com.distribuidoraferreira.backend.enums;

import lombok.Getter;

@Getter
public enum StatusComanda {
    ABERTA("ABERTA"),
    FECHADA("FECHADA");

    private final String status;

    StatusComanda(String status) { this.status = status; }
}
