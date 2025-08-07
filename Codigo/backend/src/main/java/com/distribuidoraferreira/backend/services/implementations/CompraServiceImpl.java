package com.distribuidoraferreira.backend.services.implementations;

import com.distribuidoraferreira.backend.dtos.BasicResponse;
import com.distribuidoraferreira.backend.dtos.CompraRequest;
import com.distribuidoraferreira.backend.dtos.CompraResponse;
import com.distribuidoraferreira.backend.dtos.GenericResponse;
import com.distribuidoraferreira.backend.dtos.MovimentacaoEstoqueRequest;
import com.distribuidoraferreira.backend.enums.TipoMovimentacao;
import com.distribuidoraferreira.backend.mappers.CompraMapper;
import com.distribuidoraferreira.backend.mappers.MovimentacaoEstoqueMapper;
import com.distribuidoraferreira.backend.models.Compra;
import com.distribuidoraferreira.backend.models.MovimentacaoEstoque;
import com.distribuidoraferreira.backend.models.Produto;
import com.distribuidoraferreira.backend.repositories.CompraRepository;
import com.distribuidoraferreira.backend.repositories.ProdutoRepository;
import com.distribuidoraferreira.backend.services.interfaces.CompraService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompraServiceImpl implements CompraService {

    private final CompraRepository compraRepository;

    private final CompraMapper compraMapper;

    private final MovimentacaoEstoqueMapper movimentacaoEstoqueMapper;

    private final ProdutoRepository produtoRepository;

    public GenericResponse<String> validarCompra(CompraRequest compraRequest) {
        for (MovimentacaoEstoqueRequest movimentacaoEstoqueRequest : compraRequest.getMovimentacaoEstoqueRequests()) {
            Produto produto = produtoRepository.findById(movimentacaoEstoqueRequest.getProdutoId()).orElse(null);

            if (produto == null) {
                return new BasicResponse<>("Produto não encontrado", 404);
            }

            if (checarEstoqueSuficiente(movimentacaoEstoqueRequest.getQuantidade(), produto.getQuantidadeEstoque())) {
                return new BasicResponse<>("Estoque insuficiente", 400);

            }

        }

        if (compraRequest.getMovimentacaoEstoqueRequests().isEmpty()) {
            return new BasicResponse<>("Nenhum produto adicionado", 400);
        }

        return new BasicResponse<>("Compra validada", 200);
    }

    @Override
    public GenericResponse<String> addCompra(CompraRequest compraRequest) {
        Compra compra = compraMapper.compraRequestToCompra(compraRequest);
        setMovimentacoes(compra, compraRequest.getMovimentacaoEstoqueRequests());

        compraRepository.save(compra);

        // saveMovimentacoes(compraRequest.getMovimentacaoEstoqueRequests());

        return new BasicResponse<>("Compra adicionada com sucesso", 201);
    }

    @Override
    public GenericResponse<List<CompraResponse>> getCompras() {
        List<Compra> compras = compraRepository.findAll();

        List<CompraResponse> compraResponses = compraMapper.comprasToCompraResponses(compras);

        return new BasicResponse<List<CompraResponse>>(compraResponses, 200);
    }

    @Override
    public GenericResponse<CompraResponse> getCompraById(Long id) {
        Optional<Compra> compra = compraRepository.findById(id);

        if (compra.isEmpty()) {
            return new BasicResponse<>(null, 404);
        }

        CompraResponse compraResponse = compraMapper.compraToCompraResponse(compra.get());

        return new BasicResponse<>(compraResponse, 200);
    }

    private void setMovimentacoes(Compra compra, List<MovimentacaoEstoqueRequest> movimentacaoRequests) {
        List<MovimentacaoEstoque> movimentacoes = new ArrayList<>();

        for (MovimentacaoEstoqueRequest movimentacaoRequest : movimentacaoRequests) {
            MovimentacaoEstoque movimentacaoEstoque = createMovimentacaoCompra(movimentacaoRequest);
            movimentacaoEstoque.setCompra(compra);

            movimentacoes.add(movimentacaoEstoque);
        }

        compra.setMovimentacoesEstoque(movimentacoes);
    }

    private MovimentacaoEstoque createMovimentacaoCompra(MovimentacaoEstoqueRequest movimentacaoEstoqueRequest) {
        MovimentacaoEstoque movimentacaoEstoque = movimentacaoEstoqueMapper
                .movimentacaoEstoqueRequestToMovimentacaoEstoque(movimentacaoEstoqueRequest);

        movimentacaoEstoque.setTipo(TipoMovimentacao.ENTRADA);

        Produto produto = produtoRepository
                .findById(movimentacaoEstoqueRequest.getProdutoId()).orElse(null);

        // if (produto == null) {
        // return new BasicResponse<>("Produto não encontrado", 404);
        // }

        produto.aumentarQuantidadeEstoque(movimentacaoEstoque.getQuantidade());

        movimentacaoEstoque.setProduto(produto);

        return movimentacaoEstoque;
    }

    private boolean checarEstoqueSuficiente(int entrada, int atual) {
        return entrada > atual;
    }
}
