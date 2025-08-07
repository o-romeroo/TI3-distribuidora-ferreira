package com.distribuidoraferreira.backend.services.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.distribuidoraferreira.backend.dtos.BasicResponse;
import com.distribuidoraferreira.backend.dtos.ContaClienteAlterRequest;
import com.distribuidoraferreira.backend.dtos.ContaClienteRequest;
import com.distribuidoraferreira.backend.dtos.ContaClienteResponse;
import com.distribuidoraferreira.backend.dtos.GenericResponse;
import com.distribuidoraferreira.backend.dtos.PagamentoRequest;
import com.distribuidoraferreira.backend.enums.StatusCliente;
import com.distribuidoraferreira.backend.enums.StatusVenda;
import com.distribuidoraferreira.backend.mappers.ContaClienteMapper;
import com.distribuidoraferreira.backend.models.Caixa;
import com.distribuidoraferreira.backend.models.ContaCliente;
import com.distribuidoraferreira.backend.models.Venda;
import com.distribuidoraferreira.backend.repositories.CaixaRepository;
import com.distribuidoraferreira.backend.repositories.ContaClienteRepository;
import com.distribuidoraferreira.backend.repositories.VendaRepository;
import com.distribuidoraferreira.backend.services.interfaces.ContaClienteService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContaClienteServiceImpl implements ContaClienteService {

    private final ContaClienteRepository contaClienteRepository;
    private final ContaClienteMapper contaClienteMapper;

    @Autowired
    private final CaixaRepository caixaRepository;

    @Autowired
    private final VendaRepository vendaRepository;

    @Override
    public GenericResponse<ContaClienteResponse> addContaCliente(ContaClienteRequest contaClienteRequest) {
        ContaCliente contaCliente = contaClienteMapper.contaClienteRequestToContaCliente(contaClienteRequest);
        contaCliente.setStatus(StatusCliente.ADIMPLENTE);
        contaCliente.setSaldoDevedor(0.0);
        contaCliente.setTotalPago(0.0);
        contaCliente.setTotalPagoPix(0.0);
        contaCliente.setTotalPagoDinheiro(0.0);
        contaCliente.setTotalPagoCredito(0.0);
        contaCliente.setTotalPagoDebito(0.0);
        contaCliente = contaClienteRepository.save(contaCliente);

        ContaClienteResponse response = contaClienteMapper.contaClienteToContaClienteResponse(contaCliente);
        return new BasicResponse<>(response, 201);
    }

    @Override
    public GenericResponse<ContaClienteResponse> getContaClienteById(Long id) {
        Optional<ContaCliente> contaCliente = contaClienteRepository.findById(id);

        if (contaCliente.isEmpty()) {
            return new BasicResponse<>(null, 404);
        }

        ContaClienteResponse response = contaClienteMapper.contaClienteToContaClienteResponse(contaCliente.get());
        return new BasicResponse<>(response, 200);
    }

    @Override
    public GenericResponse<List<ContaClienteResponse>> getAllContaClientes() {
        List<ContaCliente> contasCliente = contaClienteRepository.findAll();

        List<ContaClienteResponse> response = contaClienteMapper
                .contaClienteListToContaClienteResponseList(contasCliente);

        return new BasicResponse<>(response, 200);
    }

    @Override
    public GenericResponse<List<ContaClienteResponse>> getAllContaClientesInadimplentes() {
        List<ContaCliente> contasClienteInadimplentes = contaClienteRepository.findAllInadimplentes();

        List<ContaClienteResponse> response = contaClienteMapper
                .contaClienteListToContaClienteResponseList(contasClienteInadimplentes);

        return new BasicResponse<>(response, 200);
    }

    @Override
    public GenericResponse<String> pagarContaCliente(PagamentoRequest pagamentoRequest) {

        Optional<ContaCliente> contaClienteOptional = contaClienteRepository.findById(pagamentoRequest.getIdCliente());
        Optional<Venda> vendaOptional = vendaRepository.findById(pagamentoRequest.getIdVenda());
        Optional<Caixa> caixaOptional = caixaRepository.findById(vendaOptional.get().getCaixa().getId());

        if (contaClienteOptional.isEmpty()) {
            return new BasicResponse<>("Cliente da comanda não encontrado", 404);
        }

        if (caixaOptional.isEmpty()) {
            return new BasicResponse<>("Caixa não encontrado", 404);
        }

        if (vendaOptional.isEmpty()) {
            return new BasicResponse<>("Venda não encontrada", 404);
        }

        if (vendaOptional.get().getStatus().equals(StatusVenda.CONCLUIDA)) {
            return new BasicResponse<>("Venda já concluida", 400);
        }

        if (pagamentoRequest.getValorPago() <= 0) {
            return new BasicResponse<>("Valor pago inválido. O valor deve ser maior que zero.", 400);
        }

        if (contaClienteOptional.isEmpty()) {
            return new BasicResponse<>(null, 404);
        }

        ContaCliente contaCliente = contaClienteOptional.get();

        if (contaCliente.getStatus() != StatusCliente.INADIMPLENTE) {
            return new BasicResponse<>("Cliente não está inadimplente.", 400);
        }

        if (((pagamentoRequest.getValorPago() + vendaOptional.get().getTotalPago()) > vendaOptional.get()
                .getTotalVenda()) || pagamentoRequest.getValorPago() > contaCliente.getSaldoDevedor()) {
            return new BasicResponse<>(
                    "Valor pago inválido. O valor pago não pode ser maior que o valor de dívida atual.", 400);
        }

        Double novoSaldoDevedor = (contaCliente.getSaldoDevedor() - pagamentoRequest.getValorPago());
        Double restanteAPagarVenda = (vendaOptional.get().getTotalVenda()
                - (vendaOptional.get().getTotalPago() + pagamentoRequest.getValorPago()));

        if (novoSaldoDevedor == 0.0) {
            contaCliente.setStatus(StatusCliente.ADIMPLENTE);
        }

        if (restanteAPagarVenda == 0.0) {
            vendaOptional.get().setStatus(StatusVenda.CONCLUIDA);
            vendaOptional.get().setMetodoPagamento(pagamentoRequest.getMetodoPagamento());
        }

        contaCliente.setSaldoDevedor(novoSaldoDevedor);
        contaCliente.setTotalPago(contaCliente.getTotalPago() + pagamentoRequest.getValorPago());

        switch (pagamentoRequest.getMetodoPagamento()) {
            case "PIX" ->
                vendaOptional.get()
                        .setTotalPagoPix(vendaOptional.get().getTotalPagoPix() + vendaOptional.get().getTotalVenda());
            case "DINHEIRO" -> vendaOptional.get()
                    .setTotalPagoDinheiro(
                            vendaOptional.get().getTotalPagoDinheiro() + vendaOptional.get().getTotalVenda());
            case "CREDITO" -> vendaOptional.get()
                    .setTotalPagoCredito(
                            vendaOptional.get().getTotalPagoCredito() + vendaOptional.get().getTotalVenda());
            case "DEBITO" -> vendaOptional.get()
                    .setTotalPagoDebito(vendaOptional.get().getTotalPagoDebito() + vendaOptional.get().getTotalVenda());
            default -> {
            }
        }

        vendaOptional.get().setTotalPago(vendaOptional.get().getTotalPago() + pagamentoRequest.getValorPago());

        caixaOptional.get()
                .setFaturamentoDia(caixaOptional.get().getFaturamentoDia() + pagamentoRequest.getValorPago());

        caixaRepository.save(caixaOptional.get());
        vendaRepository.save(vendaOptional.get());
        contaClienteRepository.save(contaCliente);

        return new BasicResponse<>("Pagamento realizado com sucesso.", 200);
    }

    @Override
    public GenericResponse<String> atualizarInfosContaCliente(ContaClienteAlterRequest contaClienteAlterRequest) {
        Optional<ContaCliente> contaClienteOptional = contaClienteRepository
                .findById(contaClienteAlterRequest.getIdCliente());

        if (contaClienteOptional.isEmpty()) {
            return new BasicResponse<>(null, 404);
        }

        ContaCliente contaCliente = contaClienteOptional.get();

        if (contaClienteAlterRequest.getNomeCliente() == null || contaClienteAlterRequest.getNomeCliente().isEmpty()) {
            return new BasicResponse<>("Nome do cliente inválido", 400);
        }

        contaCliente.setNomeCliente(contaClienteAlterRequest.getNomeCliente());
        contaCliente.setTelefone(contaClienteAlterRequest.getTelefone());

        contaClienteRepository.save(contaCliente);

        return new BasicResponse<>("Dados atualizados com sucesso", 200);
    }
}