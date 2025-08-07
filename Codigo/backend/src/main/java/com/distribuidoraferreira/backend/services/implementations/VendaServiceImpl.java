package com.distribuidoraferreira.backend.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.distribuidoraferreira.backend.dtos.BasicResponse;
import com.distribuidoraferreira.backend.dtos.GenericResponse;
import com.distribuidoraferreira.backend.dtos.MovimentacaoEstoqueRequest;
import com.distribuidoraferreira.backend.dtos.VendaPendenteResponse;
import com.distribuidoraferreira.backend.dtos.VendaRequest;
import com.distribuidoraferreira.backend.dtos.VendaResponse;
import com.distribuidoraferreira.backend.enums.StatusCliente;
import com.distribuidoraferreira.backend.enums.StatusVenda;
import com.distribuidoraferreira.backend.enums.TipoMovimentacao;
import com.distribuidoraferreira.backend.mappers.MovimentacaoEstoqueMapper;
import com.distribuidoraferreira.backend.mappers.VendaMapper;
import com.distribuidoraferreira.backend.models.Caixa;
import com.distribuidoraferreira.backend.models.ContaCliente;
import com.distribuidoraferreira.backend.models.MovimentacaoEstoque;
import com.distribuidoraferreira.backend.models.Produto;
import com.distribuidoraferreira.backend.models.Venda;
import com.distribuidoraferreira.backend.repositories.CaixaRepository;
import com.distribuidoraferreira.backend.repositories.ContaClienteRepository;
import com.distribuidoraferreira.backend.repositories.ProdutoRepository;
import com.distribuidoraferreira.backend.repositories.VendaRepository;
import com.distribuidoraferreira.backend.services.interfaces.CaixaService;
import com.distribuidoraferreira.backend.services.interfaces.VendaService;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VendaServiceImpl implements VendaService {

    private final VendaRepository vendaRepository;

    private final VendaMapper vendaMapper;

    private final ProdutoRepository produtoRepository;

    private final MovimentacaoEstoqueMapper movimentacaoEstoqueMapper;

    private final CaixaRepository caixaRepository;

    private final ContaClienteRepository clienteRepository;

    private final CaixaService caixaService;

    @Override
    public GenericResponse<String> addVenda(VendaRequest vendaRequest) {

        if (!caixaService.isCaixaAberto().getEntity()) {
            return new BasicResponse<>("Caixa do dia nao foi aberto ainda!", 400);
        }

        if (vendaRequest.movimentacaoEstoque().isEmpty()) {
            return new BasicResponse<>("Nao foi possivel criar a venda, movimentacao de estoque vazia", 400);
        }

        if (vendaRequest.metodoPagamento().equals("CONTACLIENTE")
                && vendaRequest.contaCliente().getIdCliente() == null) {
            return new BasicResponse<>("Nao foi possivel criar a venda, conta cliente nao informada", 400);
        }

        vendaRequest.movimentacaoEstoque().forEach(action -> {
            if (action.getQuantidade() <= 0) {
                throw new IllegalArgumentException("Quantidade de produto invalida");
            }

            if (action.getPrecoUnitario() <= 0) {
                throw new IllegalArgumentException("Preco unitario invalido");
            }

            if (action.getQuantidade() > produtoRepository.findById(action.getProdutoId()).get()
                    .getQuantidadeEstoque()) {
                throw new IllegalArgumentException("Quantidade de produto insuficiente");
            }

            if (action.getProdutoId() == null) {
                throw new IllegalArgumentException("Produto nao informado");
            }

        });

        Venda venda = vendaMapper.vendaRequestToVenda(vendaRequest);
        ContaCliente contaCliente = new ContaCliente();

        setMovimentacoes(venda, vendaRequest.movimentacaoEstoque());
        setCaixaVenda(venda);
        setStatusVenda(venda);
        if (venda.getMetodoPagamento().equals("CONTACLIENTE")) {
            venda.setMetodoPagamento("A_PAGAR");
            contaCliente.setId(vendaRequest.contaCliente().getIdCliente());
            setContaCliente(venda, contaCliente);
        }

        venda.getCaixa().setFaturamentoDia(venda.getCaixa().getFaturamentoDia() + venda.getTotalVenda());

        vendaRepository.saveAndFlush(venda);

        return new BasicResponse<>("Venda criada com sucesso", 200);
    }

    @Override
    public GenericResponse<String> deleteVendaById(Long id) {
        if (!caixaService.isCaixaAberto().getEntity()) {
            return new BasicResponse<>("Caixa do dia nao foi aberto ainda!", 400);
        }

        vendaRepository.deleteById(id);

        return new BasicResponse<>("Venda removida com sucesso", 200);
    }

    @Override
    public GenericResponse<VendaResponse> getVendaById(Long id) {
        Venda venda = vendaRepository.findById(id).orElse(null);
        VendaResponse vendaResponse = vendaMapper.vendaToVendaResponse(venda);
        return new BasicResponse<>(vendaResponse, 201);
    }

    @Override
    public GenericResponse<List<VendaResponse>> getVendaByIdComanda(Long idComanda) {
        List<Venda> vendas = vendaRepository.getVendaByIdComanda(idComanda);

        List<VendaResponse> vendaResponse = vendaMapper.vendasToVendaResponses(vendas);
        return new BasicResponse<>(vendaResponse, 200);
    }

    @Override
    public GenericResponse<List<VendaResponse>> getVendas() {
        List<Venda> vendas = vendaRepository.findVendasByIdDesc();
        List<VendaResponse> vendaReponses = vendaMapper.vendasToVendaResponses(vendas);
        return new BasicResponse<>(vendaReponses, 200);
    }

    @Override
    public GenericResponse<List<VendaPendenteResponse>> getVendasPendentesPorCliente(Long id) {
        List<Venda> vendas = vendaRepository.findVendasPendentesPorCliente(id);
        List<VendaPendenteResponse> vendaReponses = new ArrayList<>();
        vendas.forEach(venda -> {
            vendaReponses.add(vendaMapper.vendaToVendaPendenteResponse(venda));
        });

        return new BasicResponse<>(vendaReponses, 200);
    }

    @Override
    public GenericResponse<List<VendaResponse>> getVendasByDataHora() {
        List<Venda> vendas = vendaRepository.findAllByOrderByDataHoraDesc();
        List<VendaResponse> vendaReponses = vendaMapper.vendasToVendaResponses(vendas);
        return new BasicResponse<>(vendaReponses, 200);
    }

    @Override
    public GenericResponse<List<VendaPendenteResponse>> getVendasPendentes() {
        List<Venda> vendas = vendaRepository.findVendasByStatus(StatusVenda.PENDENTE);
        List<VendaPendenteResponse> vendaReponses = vendaMapper.vendasToVendaPendenteResponses(vendas);
        return new BasicResponse<>(vendaReponses, 200);
    }

    @Override
    public GenericResponse<List<VendaResponse>> getVendasByStatus(StatusVenda status) {
        List<Venda> vendas = vendaRepository.findVendasByStatus(status);
        List<VendaResponse> vendaReponses = vendaMapper.vendasToVendaResponses(vendas);
        return new BasicResponse<>(vendaReponses, 200);
    }

    private void setMovimentacoes(Venda venda, List<MovimentacaoEstoqueRequest> movimentacaoRequests) {
        List<MovimentacaoEstoque> movimentacoes = new ArrayList<>();

        for (MovimentacaoEstoqueRequest movimentacaoRequest : movimentacaoRequests) {
            MovimentacaoEstoque movimentacaoEstoque = createMovimentacaoVenda(movimentacaoRequest);
            movimentacaoEstoque.setVenda(venda);

            movimentacoes.add(movimentacaoEstoque);
        }

        double totalVenda = 0.0;
        for (MovimentacaoEstoque movimentacaoEstoque : movimentacoes) {
            totalVenda += movimentacaoEstoque.getPrecoUnitario() * movimentacaoEstoque.getQuantidade();
        }

        venda.setTotalVenda(totalVenda);
        venda.setMovimentacoesEstoque(movimentacoes);
    }

    private void setStatusVenda(Venda venda) {
        if (venda.getMetodoPagamento().equals("CONTACLIENTE")) {
            venda.setStatus(StatusVenda.PENDENTE);
        } else {
            venda.setStatus(StatusVenda.CONCLUIDA);
        }
    }

    private void setContaCliente(Venda venda, ContaCliente cc) {
        Optional<ContaCliente> contaCliente = this.clienteRepository.findById(cc.getId());
        contaCliente.get().setStatus(StatusCliente.INADIMPLENTE);
        ContaCliente c = clienteRepository.save(contaCliente.get());
        if (contaCliente.isPresent()) {
            venda.setContaCliente(c);
        }
    }

    private void setCaixaVenda(Venda venda) {
        Optional<Caixa> caixa = caixaRepository.findCaixaByStatusAberto();
        if (caixa.isPresent()) {
            venda.setCaixa(caixa.get());
        }
    }

    private MovimentacaoEstoque createMovimentacaoVenda(MovimentacaoEstoqueRequest movimentacaoEstoqueRequest) {
        MovimentacaoEstoque movimentacaoEstoque = movimentacaoEstoqueMapper
                .movimentacaoEstoqueRequestToMovimentacaoEstoque(movimentacaoEstoqueRequest);

        movimentacaoEstoque.setTipo(TipoMovimentacao.SAIDA);

        Produto produto = produtoRepository
                .findById(movimentacaoEstoqueRequest.getProdutoId()).orElse(null);

        produto.diminuirQuantidadeEstoque(movimentacaoEstoque.getQuantidade());

        movimentacaoEstoque.setProduto(produto);

        return movimentacaoEstoque;
    }
}