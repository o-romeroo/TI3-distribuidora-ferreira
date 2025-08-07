package com.distribuidoraferreira.backend.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.distribuidoraferreira.backend.dtos.BasicResponse;
import com.distribuidoraferreira.backend.dtos.ComandaRequest;
import com.distribuidoraferreira.backend.dtos.ComandaResponse;
import com.distribuidoraferreira.backend.dtos.GenericResponse;
import com.distribuidoraferreira.backend.dtos.ItemComandaRequest;
import com.distribuidoraferreira.backend.dtos.PagamentoComandaRequest;
import com.distribuidoraferreira.backend.enums.StatusCaixa;
import com.distribuidoraferreira.backend.enums.StatusCliente;
import com.distribuidoraferreira.backend.enums.StatusComanda;
import com.distribuidoraferreira.backend.enums.StatusVenda;
import com.distribuidoraferreira.backend.enums.TipoMovimentacao;
import com.distribuidoraferreira.backend.mappers.ComandaMapper;
import com.distribuidoraferreira.backend.mappers.MovimentacaoEstoqueMapper;
import com.distribuidoraferreira.backend.models.Caixa;
import com.distribuidoraferreira.backend.models.Comanda;
import com.distribuidoraferreira.backend.models.ContaCliente;
import com.distribuidoraferreira.backend.models.MovimentacaoEstoque;
import com.distribuidoraferreira.backend.models.Produto;
import com.distribuidoraferreira.backend.models.Venda;
import com.distribuidoraferreira.backend.repositories.CaixaRepository;
import com.distribuidoraferreira.backend.repositories.ComandaRepository;
import com.distribuidoraferreira.backend.repositories.ContaClienteRepository;
import com.distribuidoraferreira.backend.repositories.ProdutoRepository;
import com.distribuidoraferreira.backend.repositories.VendaRepository;
import com.distribuidoraferreira.backend.services.interfaces.ComandaService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ComandaServiceImpl implements ComandaService {

    private final ComandaRepository comandaRepository;

    private final VendaRepository vendaRepository;

    private final ComandaMapper comandaMapper;

    private final CaixaRepository caixaRepository;

    private final ContaClienteRepository clienteRepository;

    private final ProdutoRepository produtoRepository;

    private final MovimentacaoEstoqueMapper movimentacaoEstoqueMapper;

    @Override
    public GenericResponse<String> addComanda(ComandaRequest comandaRequest) {
        if (!isCaixaAberto()) {
            return new BasicResponse<>("Caixa do dia nao foi aberto ainda!", 400);
        }

        if (clienteRepository.findById(comandaRequest.idCliente()).isEmpty()) {
            return new BasicResponse<>("Cliente não encontrado", 404);
        }

        boolean clientePossuiComandaAberta = comandaRepository.findComandaByIdCliente(comandaRequest.idCliente())
                .stream()
                .anyMatch(comanda -> comanda.getStatus() == StatusComanda.ABERTA);

        if (clientePossuiComandaAberta) {
            return new BasicResponse<>("Cliente já possui comanda aberta", 400);
        }

        Comanda comanda = comandaMapper.comandaRequestToComanda(comandaRequest);
        comanda.setIdCaixa(caixaRepository.findCaixaByStatusAberto().get().getId());
        comandaRepository.save(comanda);

        return new BasicResponse<>("Comanda adicionada com sucesso", 201);
    }

    @Override
    public GenericResponse<String> updateComandaById(Long id, ComandaRequest comandaRequest) {
        if (!isCaixaAberto()) {
            return new BasicResponse<>("Caixa do dia nao foi aberto ainda!", 400);
        }

        Optional<Comanda> comanda = comandaRepository.findById(id);

        if (comanda.isEmpty()) {
            return new BasicResponse<>("Comanda não encontrada", 404);
        }

        Comanda updatedComanda = comandaMapper.comandaRequestToComanda(comandaRequest);
        updatedComanda.setId(comanda.get().getId());

        return new BasicResponse<>("Comanda atualizada com sucesso", 200);
    }

    @Override
    public GenericResponse<List<ComandaResponse>> getComandas() {

        Optional<Caixa> caixaId = caixaRepository.findCaixaByStatusAberto();

        if (caixaId.isEmpty()) {
            return new BasicResponse<>(null, 404);
        }

        List<Comanda> comandas = comandaRepository.findComandaByIdCaixa(caixaId.get().getId());

        List<ComandaResponse> comandasResponse = comandaMapper.comandasToComandaResponses(comandas);

        return new BasicResponse<>(comandasResponse, 200);
    }

    @Override
    public GenericResponse<List<ComandaResponse>> getComandasByStatus(String status) {
        List<Comanda> comandas = comandaRepository.findComandaByStatus(status);
        List<ComandaResponse> comandaResponses = comandaMapper.comandasToComandaResponses(comandas);
        return new BasicResponse<>(comandaResponses, 200);
    }

    @Override
    public GenericResponse<ComandaResponse> getComandaById(Long id) {
        Optional<Comanda> comanda = comandaRepository.findById(id);

        if (comanda.isEmpty()) {
            return new BasicResponse<>(null, 404);
        }

        ComandaResponse response = comandaMapper.comandaToComandaResponse(comanda.get());
        return new BasicResponse<>(response, 200);
    }

    @Override
    public GenericResponse<List<ComandaResponse>> getComandasByIdCliente(Long idCliente) {
        List<Comanda> comandas = comandaRepository.findComandaByIdCliente(idCliente);
        List<ComandaResponse> comandaResponses = comandaMapper.comandasToComandaResponses(comandas);
        return new BasicResponse<>(comandaResponses, 200);
    }

    private boolean isCaixaAberto() {
        Optional<Caixa> caixa = caixaRepository.findCaixaByStatusAberto();

        return caixa.isPresent() && caixa.get().getStatus() == StatusCaixa.ABERTO;
    }

    @Override
    public GenericResponse<String> addItemComanda(Long id, List<ItemComandaRequest> itemComandaRequest) {
        Optional<Comanda> comanda = comandaRepository.findById(id);
        Optional<Caixa> caixa = caixaRepository.findCaixaByStatusAberto();
        GenericResponse<String> statusValidationComanda = validarComanda(id, comanda);
        GenericResponse<String> statusValidationItemComanda = validarItemComanda(id, itemComandaRequest);

        if (!caixa.isPresent()) {
            return new BasicResponse<>("Caixa do dia nao foi aberto ainda!", 400);
        }

        if (statusValidationComanda.getStatus() != 200) {
            return statusValidationComanda;
        }

        if (statusValidationItemComanda.getStatus() != 200) {
            return statusValidationItemComanda;
        }

        Venda venda = new Venda();
        venda.setStatus(StatusVenda.PENDENTE);
        venda.setTotalVenda(0.0);
        venda.setCaixa(caixa.get());
        venda.setTotalPago(0.0);
        venda.setTotalPagoDinheiro(0.0);
        venda.setTotalPagoCredito(0.0);
        venda.setTotalPagoDebito(0.0);
        venda.setTotalPagoPix(0.0);
        venda.setMetodoPagamento("A_PAGAR");
        venda.setMovimentacoesEstoque(new ArrayList<>());

        Optional<ContaCliente> contaCliente = clienteRepository.findById(comanda.get().getIdCliente());

        if (contaCliente.isEmpty()) {
            return new BasicResponse<>("Cliente da comanda não encontrado", 404);
        }

        venda.setContaCliente(contaCliente.get());
        addItemVendaComanda(venda, itemComandaRequest);
        comanda.get().addVenda(venda);
        comandaRepository.save(comanda.get());

        return new BasicResponse<>("Item adicionado com sucesso", 200);
    }

    private GenericResponse<String> addItemVendaComanda(Venda venda, List<ItemComandaRequest> itemComandaRequest) {

        for (ItemComandaRequest item : itemComandaRequest) {
            Optional<Produto> produto = produtoRepository.findById(item.idProduto());
            if (produto.isEmpty()) {
                return new BasicResponse<>("Produto não encontrado", 404);
            }

            MovimentacaoEstoque movimentacaoEstoque = new MovimentacaoEstoque();
            movimentacaoEstoque.setProduto(produto.get());
            movimentacaoEstoque.setQuantidade(item.quantidade());
            movimentacaoEstoque.setTipo(TipoMovimentacao.SAIDA);
            movimentacaoEstoque.setVenda(venda);
            movimentacaoEstoque.setValor(movimentacaoEstoque.calcularValorTotalMovimentacao());
            movimentacaoEstoque.setPrecoUnitario(produto.get().getPrecoConsumo());

            venda.getMovimentacoesEstoque().add(movimentacaoEstoque);
            venda.setTotalVenda(venda.getTotalVenda() + movimentacaoEstoque.calcularValorTotalMovimentacao());

            Optional<ContaCliente> contaCliente = clienteRepository.findById(venda.getContaCliente().getId());

            if (contaCliente.isEmpty()) {
                return new BasicResponse<>("Cliente não encontrado", 404);
            }

            contaCliente.get().setSaldoDevedor(
                    contaCliente.get().getSaldoDevedor() + movimentacaoEstoque.calcularValorTotalMovimentacao());

            contaCliente.get().setStatus(StatusCliente.INADIMPLENTE);

            venda.setContaCliente(contaCliente.get());
            vendaRepository.save(venda);

            produto.get().diminuirQuantidadeEstoque(item.quantidade());
            produtoRepository.save(produto.get());
        }

        return new BasicResponse<>("Item adicionado com sucesso", 200);
    }

    @Override
    public GenericResponse<String> concluirVendasComanda(PagamentoComandaRequest comandaRequest) {
        Optional<Comanda> comanda = comandaRepository.findById(comandaRequest.getIdComanda());
        Optional<ContaCliente> clienteComanda = clienteRepository.findById(comanda.get().getIdCliente());

        if (clienteComanda.isEmpty()) {
            return new BasicResponse<>("Cliente não encontrado para esta comanda", 404);
        }

        if (comandaRequest.getMetodoPagamento().equals("A_PAGAR")) {
            fecharComandaById(comanda.get().getId());
            return new BasicResponse<>("Vendas salvas ao cliente " + clienteComanda.get().getNomeCliente(), 200);
        }

        if (comanda.isEmpty()) {
            return new BasicResponse<>("Comanda não encontrada", 404);
        }

        if (comanda.get().getStatus() == StatusComanda.FECHADA) {
            return new BasicResponse<>("Comanda fechada", 400);
        }

        if (comanda.get().getVenda().isEmpty()) {
            return new BasicResponse<>("Comanda sem vendas", 400);
        }

        Optional<ContaCliente> contaCliente = clienteRepository.findById(comanda.get().getIdCliente());
        Optional<Caixa> caixa = caixaRepository.findCaixaByStatusAberto();

        if (caixa.isEmpty()) {
            return new BasicResponse<>("Caixa do dia nao foi aberto ainda!", 400);
        }

        if (contaCliente.isEmpty()) {
            return new BasicResponse<>("Cliente não encontrado", 404);
        }

        comanda.get().getVenda().forEach(venda -> {
            if (venda.getStatus() == StatusVenda.PENDENTE) {
                venda.setStatus(StatusVenda.CONCLUIDA);
                venda.setMetodoPagamento(comandaRequest.getMetodoPagamento());

                switch (comandaRequest.getMetodoPagamento()) {
                    case "PIX" ->
                        venda.setTotalPagoPix(venda.getTotalPagoPix() + venda.getTotalVenda());
                    case "DINHEIRO" -> venda
                            .setTotalPagoDinheiro(venda.getTotalPagoDinheiro() + venda.getTotalVenda());
                    case "CREDITO" -> venda
                            .setTotalPagoCredito(venda.getTotalPagoCredito() + venda.getTotalVenda());
                    case "DEBITO" -> venda
                            .setTotalPagoDebito(venda.getTotalPagoDebito() + venda.getTotalVenda());
                    default -> {
                    }
                }

                venda.setTotalPago(venda.getTotalVenda());
                contaCliente.get().setSaldoDevedor(contaCliente.get().getSaldoDevedor() - venda.getTotalVenda());
                caixa.get().setFaturamentoDia(caixa.get().getFaturamentoDia() + venda.getTotalVenda());

                caixaRepository.save(caixa.get());
                clienteRepository.save(contaCliente.get());
                vendaRepository.save(venda);
            }
        });

        if (contaCliente.get().getSaldoDevedor() <= 0.0) {
            contaCliente.get().setStatus(StatusCliente.ADIMPLENTE);
            clienteRepository.save(contaCliente.get());
        }

        fecharComandaById(comanda.get().getId());

        return new BasicResponse<>("Vendas concluídas com sucesso", 200);
    }

    @Override
    public GenericResponse<String> fecharComandaById(Long id) {
        Optional<Comanda> comanda = comandaRepository.findById(id);

        if (comanda.isEmpty()) {
            return new BasicResponse<>("Comanda não encontrada", 404);
        }

        if (comanda.get().getStatus() == StatusComanda.FECHADA) {
            return new BasicResponse<>("Comanda já fechada", 400);
        }

        if (comanda.get().getVenda().isEmpty()) {
            return new BasicResponse<>("Comanda sem vendas", 400);
        }

        comanda.get().getVenda().forEach(venda -> {
            if (venda.getStatus() == StatusVenda.PENDENTE) {
                vendaRepository.save(venda);
            }
        });

        comanda.get().setStatus(StatusComanda.FECHADA);
        comandaRepository.save(comanda.get());

        return new BasicResponse<>("Comanda fechada com sucesso", 200);
    }

    // FUNÇÕES DE VALIDAÇÃO

    private BasicResponse<String> validarItemComanda(Long id, List<ItemComandaRequest> itemComandaRequest) {

        if (itemComandaRequest.isEmpty()) {
            return new BasicResponse<>("Lista de itens vazia", 400);
        }

        for (ItemComandaRequest item : itemComandaRequest) {
            if (item.quantidade() <= 0) {
                return new BasicResponse<>("Quantidade de produto inválida", 400);
            }

            Optional<Produto> produto = produtoRepository.findById(item.idProduto());

            if (produto.isEmpty()) {
                return new BasicResponse<>("Produto não encontrado", 404);
            }

            if (produto.get().getQuantidadeEstoque() < item.quantidade()) {
                return new BasicResponse<>("Produtos em estoque insuficiente para esta quantidade!", 400);
            }

        }

        return new BasicResponse<>("", 200);
    }

    private BasicResponse<String> validarComanda(Long id, Optional<Comanda> comanda) {
        if (comanda.isEmpty()) {
            return new BasicResponse<>("Comanda nao encontrada", 404);
        }

        if (comanda.get().getStatus() == StatusComanda.FECHADA) {
            return new BasicResponse<>("Comanda fechada", 400);
        }

        return new BasicResponse<>("", 200);
    }

}