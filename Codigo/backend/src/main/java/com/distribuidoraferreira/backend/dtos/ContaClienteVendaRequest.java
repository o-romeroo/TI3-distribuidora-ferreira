package com.distribuidoraferreira.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ContaClienteVendaRequest {
        Long idCliente;

        public ContaClienteVendaRequest() {
                this.idCliente = 0L;
        }
}