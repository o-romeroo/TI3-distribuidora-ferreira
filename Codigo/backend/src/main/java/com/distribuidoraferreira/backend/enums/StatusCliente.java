package com.distribuidoraferreira.backend.enums;

import lombok.Getter;

@Getter
public enum StatusCliente {
    INADIMPLENTE("INADIMPLENTE"),
    ADIMPLENTE("ADIMPLENTE");

    private final String status;

    StatusCliente(String status) {
        this.status = status;
    }

}
