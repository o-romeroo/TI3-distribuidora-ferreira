package com.distribuidoraferreira.backend.services;

import com.distribuidoraferreira.backend.dtos.MovimentacaoEstoqueRequest;
import com.distribuidoraferreira.backend.dtos.MovimentacaoEstoqueResponse;
import com.distribuidoraferreira.backend.enums.TipoMovimentacao;
import com.distribuidoraferreira.backend.mappers.MovimentacaoEstoqueMapper;
import com.distribuidoraferreira.backend.models.MovimentacaoEstoque;
import com.distribuidoraferreira.backend.models.Produto;
import com.distribuidoraferreira.backend.repositories.MovimentacaoEstoqueRepository;
import com.distribuidoraferreira.backend.repositories.ProdutoRepository;
import com.distribuidoraferreira.backend.services.implementations.MovimentacaoEstoqueServiceImpl;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovimentacaoEstoqueServiceImplTest {

    @Mock
    private MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private MovimentacaoEstoqueMapper movimentacaoEstoqueMapper;

    @InjectMocks
    private MovimentacaoEstoqueServiceImpl movimentacaoEstoqueService;

    private EasyRandom easyRandom;
    private MovimentacaoEstoque movimentacaoEstoque;
    private Produto produto;
    private MovimentacaoEstoqueRequest movimentacaoEstoqueRequest;
    private MovimentacaoEstoqueResponse movimentacaoEstoqueResponse;

    @BeforeEach
    public void setUp() {
        easyRandom = new EasyRandom();
        movimentacaoEstoque = easyRandom.nextObject(MovimentacaoEstoque.class);
        produto = easyRandom.nextObject(Produto.class);
        movimentacaoEstoqueRequest = easyRandom.nextObject(MovimentacaoEstoqueRequest.class);
        movimentacaoEstoqueResponse = easyRandom.nextObject(MovimentacaoEstoqueResponse.class);
    }

    @Test
    public void GIVEN_movimentacao_estoque_request_with_entrada_WHEN_addMovimentacaoEstoque_THEN_save_is_called_on_repository() {
        movimentacaoEstoqueRequest.setTipo(TipoMovimentacao.ENTRADA.getTipo());
        when(movimentacaoEstoqueMapper
                .movimentacaoEstoqueRequestToMovimentacaoEstoque(any(MovimentacaoEstoqueRequest.class)))
                .thenReturn(movimentacaoEstoque);
        when(produtoRepository.findById(any(Long.class))).thenReturn(Optional.of(produto));

        movimentacaoEstoqueService.addMovimentacaoEstoque(movimentacaoEstoqueRequest);

        verify(produtoRepository).save(any(Produto.class));
        verify(movimentacaoEstoqueRepository).save(any(MovimentacaoEstoque.class));
    }

    @Test
    public void GIVEN_movimentacao_estoque_request_with_saida_WHEN_addMovimentacaoEstoque_THEN_save_is_called_on_repository() {
        movimentacaoEstoqueRequest.setTipo(TipoMovimentacao.SAIDA.getTipo());
        when(movimentacaoEstoqueMapper
                .movimentacaoEstoqueRequestToMovimentacaoEstoque(any(MovimentacaoEstoqueRequest.class)))
                .thenReturn(movimentacaoEstoque);
        when(produtoRepository.findById(any(Long.class))).thenReturn(Optional.of(produto));

        movimentacaoEstoqueService.addMovimentacaoEstoque(movimentacaoEstoqueRequest);

        verify(produtoRepository).save(any(Produto.class));
        verify(movimentacaoEstoqueRepository).save(any(MovimentacaoEstoque.class));
    }

    @Test
    public void GIVEN_movimentacao_estoque_request_with_perda_WHEN_addMovimentacaoEstoque_THEN_save_is_called_on_repository() {
        movimentacaoEstoqueRequest.setTipo(TipoMovimentacao.PERDA.getTipo());
        when(movimentacaoEstoqueMapper
                .movimentacaoEstoqueRequestToMovimentacaoEstoque(any(MovimentacaoEstoqueRequest.class)))
                .thenReturn(movimentacaoEstoque);
        when(produtoRepository.findById(any(Long.class))).thenReturn(Optional.of(produto));

        movimentacaoEstoqueService.addMovimentacaoEstoque(movimentacaoEstoqueRequest);

        verify(produtoRepository).save(any(Produto.class));
        verify(movimentacaoEstoqueRepository).save(any(MovimentacaoEstoque.class));
    }

    @Test
    public void GIVEN_no_input_WHEN_getMovimentacoesEstoque_THEN_findAll_and_movimentacoesEstoqueToMovimentacaoEstoqueResponses_are_called() {
        when(movimentacaoEstoqueRepository.findAll()).thenReturn(Collections.singletonList(movimentacaoEstoque));
        when(movimentacaoEstoqueMapper.movimentacoesEstoqueToMovimentacaoEstoqueResponses(anyList()))
                .thenReturn(Collections.singletonList(movimentacaoEstoqueResponse));

        movimentacaoEstoqueService.getMovimentacoesEstoque();

        verify(movimentacaoEstoqueRepository).findAll();
        verify(movimentacaoEstoqueMapper).movimentacoesEstoqueToMovimentacaoEstoqueResponses(anyList());
    }

    @Test
    public void GIVEN_movimentacao_estoque_id_WHEN_getMovimentacaoEstoqueById_THEN_findById_and_movimentacaoEstoqueToMovimentacaoEstoqueResponse_are_called() {
        when(movimentacaoEstoqueRepository.findById(any(Long.class))).thenReturn(Optional.of(movimentacaoEstoque));
        when(movimentacaoEstoqueMapper.movimentacaoEstoqueToMovimentacaoEstoqueResponse(any(MovimentacaoEstoque.class)))
                .thenReturn(movimentacaoEstoqueResponse);

        movimentacaoEstoqueService.getMovimentacaoEstoqueById(movimentacaoEstoque.getId());

        verify(movimentacaoEstoqueRepository).findById(any(Long.class));
        verify(movimentacaoEstoqueMapper)
                .movimentacaoEstoqueToMovimentacaoEstoqueResponse(any(MovimentacaoEstoque.class));
    }
}