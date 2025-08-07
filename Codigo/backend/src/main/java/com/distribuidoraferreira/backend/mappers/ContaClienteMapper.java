package com.distribuidoraferreira.backend.mappers;

import com.distribuidoraferreira.backend.dtos.ContaClienteRequest;
import com.distribuidoraferreira.backend.dtos.ContaClienteResponse;
import com.distribuidoraferreira.backend.models.ContaCliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { VendaMapper.class })
public interface ContaClienteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nomeCliente", source = "request.nomeCliente")
    @Mapping(target = "telefone", source = "request.telefone")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "saldoDevedor", ignore = true)
    @Mapping(target = "totalPago", ignore = true)
    @Mapping(target = "vendas", ignore = true)
    ContaCliente contaClienteRequestToContaCliente(ContaClienteRequest request);

    @Mapping(target = "vendaResponseList", source = "entity.vendas")
    @Mapping(target = "statusCliente", source = "entity.status")
    ContaClienteResponse contaClienteToContaClienteResponse(ContaCliente entity);

    @Mapping(target = "id", source = "response.id")
    @Mapping(target = "nomeCliente", source = "response.nomeCliente")
    @Mapping(target = "telefone", source = "response.telefone")
    @Mapping(target = "vendas", ignore = true)
    @Mapping(target = "status", source = "response.statusCliente")
    @Mapping(target = "saldoDevedor", source = "response.saldoDevedor")
    ContaCliente contaClienteResponseToContaCliente(ContaClienteResponse response);

    List<ContaClienteResponse> contaClienteListToContaClienteResponseList(List<ContaCliente> contaClienteList);
}