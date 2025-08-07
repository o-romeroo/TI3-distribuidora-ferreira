package com.distribuidoraferreira.backend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DetalhesCaixa {
    Double dinheiro;
    Double pix;
    Double credito;
    Double debito;
    Double contaCliente;

    public DetalhesCaixa() {
        this.dinheiro = 0.0;
        this.pix = 0.0;
        this.credito = 0.0;
        this.debito = 0.0;
        this.contaCliente = 0.0;
    }
}
