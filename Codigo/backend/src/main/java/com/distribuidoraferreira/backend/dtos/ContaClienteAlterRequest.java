package com.distribuidoraferreira.backend.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ContaClienteAlterRequest(
        @JsonProperty("id_cliente") Long idCliente,
        @JsonProperty("nome_cliente") String nomeCliente,
        @JsonProperty("telefone") String telefone) {

        public Long getIdCliente(){
                return this.idCliente;
        }

        public String getNomeCliente(){
                return this.nomeCliente;
        }

        public String getTelefone(){
                return this.telefone;
        }
} 