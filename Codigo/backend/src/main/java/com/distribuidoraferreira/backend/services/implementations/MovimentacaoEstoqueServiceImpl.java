package com.distribuidoraferreira.backend.services.implementations;

import com.distribuidoraferreira.backend.dtos.BasicResponse;
import com.distribuidoraferreira.backend.dtos.GenericResponse;
import com.distribuidoraferreira.backend.dtos.MovimentacaoEstoqueRequest;
import com.distribuidoraferreira.backend.dtos.MovimentacaoEstoqueResponse;
import com.distribuidoraferreira.backend.enums.TipoMovimentacao;
import com.distribuidoraferreira.backend.mappers.MovimentacaoEstoqueMapper;
import com.distribuidoraferreira.backend.models.MovimentacaoEstoque;
import com.distribuidoraferreira.backend.models.Produto;
import com.distribuidoraferreira.backend.repositories.MovimentacaoEstoqueRepository;
import com.distribuidoraferreira.backend.repositories.ProdutoRepository;
import com.distribuidoraferreira.backend.services.interfaces.MovimentacaoEstoqueService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MovimentacaoEstoqueServiceImpl implements MovimentacaoEstoqueService {

    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    private final MovimentacaoEstoqueMapper movimentacaoEstoqueMapper;

    private final ProdutoRepository produtoRepository;

    @Override
    public GenericResponse<String> addMovimentacaoEstoque(MovimentacaoEstoqueRequest movimentacaoEstoqueRequest) {
        MovimentacaoEstoque movimentacaoEstoque = movimentacaoEstoqueMapper
                .movimentacaoEstoqueRequestToMovimentacaoEstoque(movimentacaoEstoqueRequest);

        Produto produto = produtoRepository
                .findById(movimentacaoEstoqueRequest.getProdutoId()).orElse(null);

        if (produto == null) {
            return new BasicResponse<>("Produto não encontrado", 404);
        }

        if (movimentacaoEstoqueRequest.getTipo().equals(TipoMovimentacao.ENTRADA.getTipo())) {

            produto.aumentarQuantidadeEstoque(movimentacaoEstoque.getQuantidade());

            movimentacaoEstoque.setProduto(produto);

            produtoRepository.save(produto);

            movimentacaoEstoqueRepository.save(movimentacaoEstoque);
        } else if (movimentacaoEstoqueRequest.getTipo().equals(TipoMovimentacao.SAIDA.getTipo())) {

            if (checarEstoqueSuficiente(movimentacaoEstoque.getQuantidade(), produto.getQuantidadeEstoque())) {
                return new BasicResponse<>("Quantidade de estoque insuficiente", 400);
            }

            produto.diminuirQuantidadeEstoque(movimentacaoEstoque.getQuantidade());
            movimentacaoEstoque.setProduto(produto);

            produtoRepository.save(produto);
            movimentacaoEstoqueRepository.save(movimentacaoEstoque);
        } else if (movimentacaoEstoqueRequest.getTipo().equals(TipoMovimentacao.PERDA.getTipo())) {
            if (checarEstoqueSuficiente(movimentacaoEstoque.getQuantidade(), produto.getQuantidadeEstoque())) {
                produto.setQuantidadeEstoque(0);
            } else {
                produto.diminuirQuantidadeEstoque(movimentacaoEstoque.getQuantidade());
            }

            movimentacaoEstoque.setProduto(produto);

            produtoRepository.save(produto);
            movimentacaoEstoqueRepository.save(movimentacaoEstoque);
        }

        return new BasicResponse<>("Movimentação de estoque adicionada com sucesso", 201);
    }

    @Override
    public GenericResponse<List<MovimentacaoEstoqueResponse>> getMovimentacoesEstoque() {
        List<MovimentacaoEstoque> movimentacoesEstoque = movimentacaoEstoqueRepository.findAll();

        List<MovimentacaoEstoqueResponse> movimentacaoEstoqueResponses = movimentacaoEstoqueMapper
                .movimentacoesEstoqueToMovimentacaoEstoqueResponses(movimentacoesEstoque);

        return new BasicResponse<List<MovimentacaoEstoqueResponse>>(movimentacaoEstoqueResponses, 200);
    }

    @Override
    public GenericResponse<MovimentacaoEstoqueResponse> getMovimentacaoEstoqueById(Long id) {
        Optional<MovimentacaoEstoque> movimentacaoEstoque = movimentacaoEstoqueRepository.findById(id);

        if (movimentacaoEstoque.isEmpty()) {
            return new BasicResponse<>(null, 404);
        }

        MovimentacaoEstoqueResponse response = movimentacaoEstoqueMapper
                .movimentacaoEstoqueToMovimentacaoEstoqueResponse(movimentacaoEstoque.get());

        return new BasicResponse<>(response, 200);
    }

    @Override
    public GenericResponse<List<MovimentacaoEstoqueResponse>> getMovimentacoesEstoqueByProdutoId(Long produtoId) {
        List<MovimentacaoEstoque> movimentacoesEstoque = movimentacaoEstoqueRepository.findAllByProdutoId(produtoId);

        List<MovimentacaoEstoqueResponse> movimentacaoEstoqueResponses = movimentacaoEstoqueMapper
                .movimentacoesEstoqueToMovimentacaoEstoqueResponses(movimentacoesEstoque);

        return new BasicResponse<List<MovimentacaoEstoqueResponse>>(movimentacaoEstoqueResponses, 200);
    }

    private boolean checarEstoqueSuficiente(int entrada, int atual) {
        return entrada > atual;
    }
}
