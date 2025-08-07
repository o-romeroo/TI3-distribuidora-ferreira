package com.distribuidoraferreira.backend.services.interfaces;

import com.distribuidoraferreira.backend.dtos.ContaClienteAlterRequest;
import com.distribuidoraferreira.backend.dtos.ContaClienteRequest;
import com.distribuidoraferreira.backend.dtos.ContaClienteResponse;
import com.distribuidoraferreira.backend.dtos.GenericResponse;
import com.distribuidoraferreira.backend.dtos.PagamentoRequest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface ContaClienteService {
    GenericResponse<ContaClienteResponse> addContaCliente(ContaClienteRequest contaClienteRequest);

    GenericResponse<ContaClienteResponse> getContaClienteById(Long id);

    GenericResponse<List<ContaClienteResponse>> getAllContaClientes();

    GenericResponse<List<ContaClienteResponse>> getAllContaClientesInadimplentes();

    GenericResponse<String> pagarContaCliente(PagamentoRequest pagamentoRequest);

    GenericResponse<String> atualizarInfosContaCliente(ContaClienteAlterRequest contaClienteAlterRequest);
}